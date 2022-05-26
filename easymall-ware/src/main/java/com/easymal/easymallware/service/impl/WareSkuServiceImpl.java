package com.easymal.easymallware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymal.easymallware.dao.WareSkuDao;
import com.easymal.easymallware.entity.WareOrderTaskDetailEntity;
import com.easymal.easymallware.entity.WareOrderTaskEntity;
import com.easymal.easymallware.entity.WareSkuEntity;
import com.easymal.easymallware.enume.OrderStatusEnum;
import com.easymal.easymallware.enume.WareTaskStatusEnum;
import com.easymal.easymallware.feign.OrderFeignService;
import com.easymal.easymallware.feign.ProductFeignService;
import com.easymal.easymallware.service.WareOrderTaskDetailService;
import com.easymal.easymallware.service.WareOrderTaskService;
import com.easymal.easymallware.service.WareSkuService;
import com.easymal.easymallware.vo.OrderItemVo;
import com.easymal.easymallware.vo.SkuLockVo;
import com.easymal.easymallware.vo.WareSkuLockVo;
import com.rabbitmq.client.Channel;
import easymall.easymallcommon.exception.NoStockException;
import easymall.easymallcommon.to.SkuHasStockVo;
import easymall.easymallcommon.to.mq.OrderTo;
import easymall.easymallcommon.to.mq.StockDetailTo;
import easymall.easymallcommon.to.mq.StockLockedTo;
import easymall.easymallcommon.utils.PageUtils;
import easymall.easymallcommon.utils.Query;
import easymall.easymallcommon.utils.R;
import jdk.internal.org.objectweb.asm.TypeReference;
import lombok.Data;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RabbitListener(queues = "stock.release.stock.queue")
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private WareOrderTaskService wareOrderTaskService;

    @Autowired
    private WareOrderTaskDetailService wareOrderTaskDetailService;

    @Autowired
    private OrderFeignService orderFeignService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        Integer count = this.baseMapper.selectCount(
                new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if (count == 0) {
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);
            //查出skuname并设置
            try {
                R info = productFeignService.info(skuId);
                Map<String, Object> data = (Map<String, Object>) info.get("skuInfo");
                wareSkuEntity.setSkuName((String) data.get("skuName"));
            } catch (Exception e) {
            }
            this.baseMapper.insert(wareSkuEntity);
        } else {
            this.baseMapper.addstock(skuId, wareId, skuNum);
        }
    }

    @Override
    public List<SkuHasStockVo> getSkuHasStocks(List<Long> ids) {
        List<SkuHasStockVo> skuHasStockVos = ids.stream().map(id -> {
            SkuHasStockVo skuHasStockVo = new SkuHasStockVo();
            skuHasStockVo.setSkuId(id);
            Integer count = baseMapper.getTotalStock(id);
            skuHasStockVo.setHasStock(count == null ? false : count > 0);
            return skuHasStockVo;
        }).collect(Collectors.toList());
        return skuHasStockVos;
    }

    @Transactional
    @Override
    public Boolean orderLockStock(WareSkuLockVo wareSkuLockVo) {
        WareOrderTaskEntity taskEntity = new WareOrderTaskEntity();
        taskEntity.setOrderSn(wareSkuLockVo.getOrderSn());
        wareOrderTaskService.save(taskEntity);

        List<OrderItemVo> itemVos = wareSkuLockVo.getLocks();
        List<SkuLockVo> lockVos = itemVos.stream().map((item) -> {
            SkuLockVo skuLockVo = new SkuLockVo();
            skuLockVo.setSkuId(item.getSkuId());
            skuLockVo.setNum(item.getCount());
            //找出所有库存大于商品数的仓库
            List<Long> wareIds = baseMapper.listWareIdsHasStock(item.getSkuId(), item.getCount());
            skuLockVo.setWareIds(wareIds);
            return skuLockVo;
        }).collect(Collectors.toList());

        for (SkuLockVo lockVo : lockVos) {
            boolean lock = true;
            Long skuId = lockVo.getSkuId();
            List<Long> wareIds = lockVo.getWareIds();
            //如果没有满足条件的仓库，抛出异常
            if (wareIds == null || wareIds.size() == 0) {
                throw new NoStockException(skuId);
            } else {
                for (Long wareId : wareIds) {
                    Long count = baseMapper.lockWareSku(skuId, lockVo.getNum(), wareId);
                    if (count == 0) {
                        lock = false;
                    } else {
                        //锁定成功，保存工作单详情
                        WareOrderTaskDetailEntity wareOrderTaskDetailEntity = new WareOrderTaskDetailEntity(null, lockVo.getSkuId(), null,
                                lockVo.getNum(), taskEntity.getId(), wareId, 1);
                        wareOrderTaskDetailService.save(wareOrderTaskDetailEntity);
                        StockLockedTo stockLockedTo = new StockLockedTo();
                        StockDetailTo stockDetailTo = new StockDetailTo();
                        BeanUtils.copyProperties(wareOrderTaskDetailEntity, stockDetailTo);
                        stockLockedTo.setDetailTo(stockDetailTo);
                        rabbitTemplate.convertAndSend("stock-event-exchange", "stock.locked", stockLockedTo);
                        lock = true;
                        break;
                    }
                }
            }
            if (!lock) {
                throw new NoStockException(skuId);
            }
        }
        return true;
    }

    /**
     * 1、没有这个订单，必须解锁库存
     * *          2、有这个订单，不一定解锁库存
     * *              订单状态：已取消：解锁库存
     * *                      已支付：不能解锁库存
     * 消息队列解锁库存
     *
     * @param stockLockedTo
     */
    @Override
    public void unlock(StockLockedTo stockLockedTo, Channel channel, Message message) throws RuntimeException {
        StockDetailTo detailTo = stockLockedTo.getDetailTo();
        WareOrderTaskDetailEntity detailEntity = wareOrderTaskDetailService.getById(detailTo.getId());
        //1.如果工作单详情不为空，说明该库存锁定成功
        if (detailEntity != null) {
            WareOrderTaskEntity taskEntity = wareOrderTaskService.getById(stockLockedTo.getId());
            R r = orderFeignService.getInfoByOrderSn(taskEntity.getOrderSn());
            if (r.getCode() == 0) {
                OrderTo order = r.getData("order", new TypeReference<OrderTo>() {
                });
                //没有这个订单||订单状态已经取消 解锁库存
                if (order == null || order.getStatus() == OrderStatusEnum.CANCLED.getCode()) {
                    //为保证幂等性，只有当工作单详情处于被锁定的情况下才进行解锁
                    if (detailEntity.getLockStatus() == WareTaskStatusEnum.Locked.getCode()) {
                        unlockStock(detailTo.getSkuId(), detailTo.getSkuNum(), detailTo.getWareId(), detailEntity.getId());
                    }
                }
            } else {
                throw new RuntimeException("远程调用订单服务失败");
            }
        } else {
            //无需解锁

        }
    }

    @Override
    public void unlock(OrderTo orderTo) {
        WareOrderTaskEntity wareOrderTaskEntity = wareOrderTaskService.getBaseMapper().selectOne(new QueryWrapper<WareOrderTaskEntity>()
                .lambda().eq(WareOrderTaskEntity::getOrderSn, orderTo.getOrderSn()));
        List<WareOrderTaskDetailEntity> orderTaskDetailEntityList = wareOrderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetailEntity>()
                .lambda().eq(WareOrderTaskDetailEntity::getTaskId, wareOrderTaskEntity.getId()));
        for (WareOrderTaskDetailEntity taskDetailEntity : orderTaskDetailEntityList) {
            unlockStock(taskDetailEntity.getSkuId(), taskDetailEntity.getSkuNum()
                    , taskDetailEntity.getWareId(), taskDetailEntity.getId());
        }
    }

    private void unlockStock(Long skuId, Integer skuNum, Long wareId, Long detailId) {
        //数据库中解锁库存数据
        baseMapper.unlockStock(skuId, skuNum, wareId);
        //更新库存工作单详情的状态
        WareOrderTaskDetailEntity detail = WareOrderTaskDetailEntity.builder()
                .id(detailId)
                .lockStatus(2).build();
        wareOrderTaskDetailService.updateById(detail);
    }

    /*private void handleReleaseLock(StockLockedTo stockLockedTo, Message message, Channel channel) {
        System.out.println("收到解锁库存的消息");
        try {
            unlock(stockLockedTo, channel, message);
        } catch (IOException e) {
        }
    }*/

    @Data
    class SkuLockVo {
        private Long skuId;
        private Integer num;
        private List<Long> wareIds;
    }

}