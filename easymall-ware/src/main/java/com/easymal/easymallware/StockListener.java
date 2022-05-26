package com.easymal.easymallware;

import com.easymal.easymallware.service.WareSkuService;
import com.rabbitmq.client.Channel;
import easymall.easymallcommon.to.mq.OrderTo;
import easymall.easymallcommon.to.mq.StockLockedTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @Description:
 * @Name: StockListener
 * @Author peipei
 * @Date 2022/5/5
 */
@Slf4j
@Component
@RabbitListener(queues = "stock.release.stock.queue")
public class StockListener {

    @Autowired
    WareSkuService wareSkuService;

    @RabbitHandler
    public void handleStockLockedRelease(StockLockedTo stockLockedTo, Message message, Channel channel) throws IOException {
        log.info("*****************开始释放库存*********************");
        try {
            wareSkuService.unlock(stockLockedTo, channel, message);
            channel.basicAck(message.getMessageProperties().getPriority(), false);
        } catch (Exception e) {
            //发生异常，则拒绝接受此消息，将此消息重新放入队列中
            log.info("调用ware解锁库存服务失败,尝试重试,{}", new Date());
            channel.basicReject(message.getMessageProperties().getPriority(), true);
        }
    }

    @RabbitHandler
    public void handleStockLockedRelease(OrderTo orderTo, Message message, Channel channel) throws IOException {
        log.info("************************从订单模块收到库存解锁的消息********************************");
        try {
            wareSkuService.unlock(orderTo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }


    }


}
