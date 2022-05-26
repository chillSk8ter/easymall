package com.easymall.seckill.scheduled;

import com.easymall.seckill.seckillService.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 秒杀商品的定时上架服务
 * @Name: SeckillScheduled
 * @Author peipei
 * @Date 2022/5/5
 */

@Service
@Slf4j
public class SeckillScheduled {
    private final String LOCK_CONSTANT = "sec:lock:";

    @Autowired
    SecKillService secKillService;

    @Autowired
    RedissonClient redissonClient;

    /**
     * 使用分布式锁，当第一台机器拿到该锁时，执行上架
     */
    @Scheduled
    public void uploadSecKillSku() {
        RLock lock = redissonClient.getLock(LOCK_CONSTANT);
        lock.lock(10, TimeUnit.MINUTES);
        try {
            secKillService.uploadSeckillSkuLatest3Days();
        } finally {
            //无论是否调用成功都需要解锁
            lock.unlock();
        }
    }


}
