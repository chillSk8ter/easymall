package com.easymall.easymallcoupon.sms.service;

import com.easymall.easymallcoupon.sms.model.SmsSkuFullReduction;
import com.baomidou.mybatisplus.extension.service.IService;
import easymall.easymallcommon.to.SkuReductionTo;

/**
 * <p>
 * 商品满减信息 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
public interface SmsSkuFullReductionService extends IService<SmsSkuFullReduction> {

    void saveSkuReductionTo(SkuReductionTo skuReductionTo);
}
