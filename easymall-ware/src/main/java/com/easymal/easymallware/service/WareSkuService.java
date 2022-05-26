package com.easymal.easymallware.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.easymal.easymallware.entity.WareSkuEntity;
import com.easymal.easymallware.vo.WareSkuLockVo;
import com.rabbitmq.client.Channel;
import easymall.easymallcommon.to.SkuHasStockVo;
import easymall.easymallcommon.to.mq.OrderTo;
import easymall.easymallcommon.to.mq.StockLockedTo;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.amqp.core.Message;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:15:25
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkuHasStocks(List<Long> ids);

    Boolean orderLockStock(WareSkuLockVo lockVo);

    void unlock(StockLockedTo stockLockedTo, Channel channel, Message message);

    void unlock(OrderTo orderTo);
}

