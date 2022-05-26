package com.easymall.seckill.seckillService.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.easymall.seckill.feign.CouponFeignService;
import com.easymall.seckill.feign.ProductFeignService;
import com.easymall.seckill.interceptor.LoginInterceptor;
import com.easymall.seckill.seckillService.SecKillService;
import com.easymall.seckill.to.SeckillSkuRedisTo;
import com.easymall.seckill.vo.SeckillSessionWithSkusVo;
import com.easymall.seckill.vo.SeckillSkuVo;
import com.easymall.seckill.vo.SkuInfoVo;
import easymall.easymallcommon.to.mq.SeckillOrderTo;
import easymall.easymallcommon.utils.R;
import easymall.easymallcommon.vo.MemberResponseVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Name: SecKillServiceImpl
 * @Author peipei
 * @Date 2022/5/5
 */
@Service
public class SecKillServiceImpl implements SecKillService {
    private final String SESSION_CACHE_PREFIX = "seckill:sessions:";

    //K: 固定值SECKILL_CHARE_PREFIX
    //V: hash，k为sessionId+"-"+skuId，v为对应的商品信息SeckillSkuRedisTo
    private final String SECKILL_CHARE_PREFIX = "seckill:skus";

    //K: SKU_STOCK_SEMAPHORE+商品随机码
    //V: 秒杀的库存件数
    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";    //+商品随机码


    @Autowired
    CouponFeignService couponFeignService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public void uploadSeckillSkuLatest3Days() {
        R r = couponFeignService.getSeckillSessionsIn3Days();
        if (r.getCode() == 0) {
            List<SeckillSessionWithSkusVo> SessionWithSkusVo = r.getData(new TypeReference<List<SeckillSessionWithSkusVo>>());
            saveSecKillSku(SessionWithSkusVo);
            saveSecKillSession(SessionWithSkusVo);
        } else {
            throw new RuntimeException("远程调用服务失败");
        }
    }

    @Override
    public List<SeckillSkuRedisTo> getCurrentSecKillSkus() {
        //获取当前时间的秒杀活动场次
        ArrayList<SeckillSkuRedisTo> seckillOrderTos = new ArrayList<>();
        Set<String> keys = redisTemplate.keys(SESSION_CACHE_PREFIX + "*");
        for (String key : keys) {
            String[] split = key.replace(SESSION_CACHE_PREFIX, "").split("_");
            long start = Long.parseLong(split[0]);
            long end = Long.parseLong(split[1]);
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis >= start && currentTimeMillis <= end) {
                List<String> skuList = redisTemplate.opsForList().range(key, -100, 100);
                BoundHashOperations ops = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
                if (!CollectionUtils.isEmpty(skuList)) {
                    for (String skuId : skuList) {
                        String jsonStr = (String) ops.get(skuId);
                        SeckillSkuRedisTo seckillSkuRedisTo = JSON.parseObject(jsonStr, SeckillSkuRedisTo.class);
                        seckillSkuRedisTo.setRandomCode("");
                        seckillOrderTos.add(seckillSkuRedisTo);
                    }
                }
            }
        }
        return seckillOrderTos;
    }

    @Override
    public SeckillSkuRedisTo getSecKillSkuById(String skuId) {
        SeckillSkuRedisTo seckillSkuRedisTo = new SeckillSkuRedisTo();
        BoundHashOperations ops = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
        Set<String> keys = ops.keys();
        for (String key : keys) {
            String[] split = key.split("_");
            if (split[1].equals(skuId)) {
                String jsonStr = (String) ops.get(key);
                seckillSkuRedisTo = JSON.parseObject(jsonStr, SeckillSkuRedisTo.class);
                return seckillSkuRedisTo;
            }
        }
        return seckillSkuRedisTo;
    }

    @Override
    public String secKill(Long killId, Integer num, String key) throws InterruptedException {
        String orderSn = null;
        BoundHashOperations ops = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
        String jsonStr = (String) ops.get(killId);
        if (!StringUtils.isEmpty(jsonStr)) {
            SeckillSkuRedisTo redisTo = JSON.parseObject(jsonStr, SeckillSkuRedisTo.class);
            Long startTime = redisTo.getStartTime();
            Long endTime = redisTo.getEndTime();
            long currentTime = System.currentTimeMillis();
            if (currentTime >= startTime && currentTime <= endTime) {
                String redis = redisTo.getPromotionSessionId() + "_" + redisTo.getSkuId();
                if (redis.equals(killId) && key.equals(redisTo.getRandomCode()) && num <= redisTo.getSeckillCount()) {
                    MemberResponseVo memberResponseVo = LoginInterceptor.threadLocal.get();
                    String secKillKey = memberResponseVo.getId() + "_" + redisTo.getSkuId();
                    long expireTime = endTime - System.currentTimeMillis();
                    //保证幂等性，让用户多次点击抢购按钮只扣减一次库存
                    Boolean setIfAbsent = redisTemplate.opsForValue().setIfAbsent(secKillKey, num.toString(), expireTime, TimeUnit.MILLISECONDS);
                    if (setIfAbsent) {
                        RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + redisTo.getRandomCode());
                        //尝试扣减改场次下sku的信号量
                        boolean tryAcquire = semaphore.tryAcquire(num, 100, TimeUnit.MILLISECONDS);
                        //如果扣减成功
                        if (tryAcquire) {
                            orderSn = IdWorker.getTimeId();
                            SeckillOrderTo seckillOrderTo = new SeckillOrderTo();
                            seckillOrderTo.setOrderSn(orderSn);
                            seckillOrderTo.setPromotionSessionId(redisTo.getPromotionSessionId());
                            seckillOrderTo.setSkuId(redisTo.getSkuId());
                            seckillOrderTo.setSeckillPrice(redisTo.getSeckillPrice());
                            seckillOrderTo.setNum(num);
                            seckillOrderTo.setMemberId(memberResponseVo.getId());


                        }


                    }


                }


            }


        }


    }

    private void saveSecKillSession(List<SeckillSessionWithSkusVo> sessionWithSkusVoList) {
        for (SeckillSessionWithSkusVo vo : sessionWithSkusVoList) {
            String key = SESSION_CACHE_PREFIX + vo.getStartTime() + "_" + vo.getEndTime();
            if (!redisTemplate.hasKey(key)) {
                List<SeckillSkuVo> relations = vo.getRelations();
                List<String> list = relations.stream().map(relation ->
                        relation.getPromotionId() + "_" + relation.getSkuId()).collect(Collectors.toList());
                redisTemplate.opsForList().leftPush(key, list);
            }
        }
    }

    private void saveSecKillSku(List<SeckillSessionWithSkusVo> sessionWithSkusVoList) {
        BoundHashOperations ops = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
        for (SeckillSessionWithSkusVo vo : sessionWithSkusVoList) {
            List<SeckillSkuVo> relations = vo.getRelations();
            for (SeckillSkuVo relation : relations) {
                String key = relation.getPromotionId() + "_" + relation.getSkuId();
                if (ops.hasKey(key)) {
                    continue;
                }
                SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
                BeanUtils.copyProperties(relation, redisTo);
                redisTo.setStartTime(vo.getStartTime().getTime());
                redisTo.setEndTime(vo.getEndTime().getTime());
                R r = productFeignService.getSkuInfoVo(relation.getSkuId());
                if (r.getCode() == 0) {
                    SkuInfoVo skuInfoVo = r.getData(new TypeReference<SkuInfoVo>());
                    redisTo.setSkuInfoVo(skuInfoVo);
                } else {
                    throw new RuntimeException("调用远程服务失败");
                }
                String randomCode = UUID.randomUUID().toString().replace("-", "");
                RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + randomCode);
                semaphore.trySetPermits(relation.getSeckillCount());
            }
        }
    }


}
