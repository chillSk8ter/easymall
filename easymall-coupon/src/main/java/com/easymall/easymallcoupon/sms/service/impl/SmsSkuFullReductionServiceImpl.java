package com.easymall.easymallcoupon.sms.service.impl;

import com.easymall.easymallcoupon.sms.model.SmsSkuFullReduction;
import com.easymall.easymallcoupon.sms.mapper.SmsSkuFullReductionMapper;
import com.easymall.easymallcoupon.sms.model.SmsSkuLadder;
import com.easymall.easymallcoupon.sms.service.SmsSkuFullReductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.easymallcoupon.sms.service.SmsSkuLadderService;
import easymall.easymallcommon.to.SkuReductionTo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品满减信息 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@Service
public class SmsSkuFullReductionServiceImpl extends ServiceImpl<SmsSkuFullReductionMapper, SmsSkuFullReduction> implements SmsSkuFullReductionService {
    @Autowired
    SmsSkuLadderService skuLadderService;

    @Override
    public void saveSkuReductionTo(SkuReductionTo skuReductionTo) {
        SmsSkuFullReduction skuFullReduction=new SmsSkuFullReduction();
        BeanUtils.copyProperties(skuReductionTo,skuFullReduction);
        save(skuFullReduction);
        SmsSkuLadder smsSkuLadder = new SmsSkuLadder();
        BeanUtils.copyProperties(skuReductionTo,smsSkuLadder);
        skuLadderService.save(smsSkuLadder);
        return;
    }
}
