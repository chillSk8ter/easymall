package com.easymall.seckill.seckillService;

import com.easymall.seckill.to.SeckillSkuRedisTo;

import java.util.List;

public interface SecKillService {

    void uploadSeckillSkuLatest3Days();

    List<SeckillSkuRedisTo> getCurrentSecKillSkus();

    SeckillSkuRedisTo getSecKillSkuById(String skuId);

    String secKill(Long killId, Integer num, String key) throws InterruptedException;

}
