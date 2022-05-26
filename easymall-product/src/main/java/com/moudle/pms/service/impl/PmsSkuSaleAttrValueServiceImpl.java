package com.moudle.pms.service.impl;

import com.moudle.pms.model.PmsSkuSaleAttrValue;
import com.moudle.pms.mapper.PmsSkuSaleAttrValueMapper;
import com.moudle.pms.service.PmsSkuSaleAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku销售属性&值 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Service
public class PmsSkuSaleAttrValueServiceImpl extends ServiceImpl<PmsSkuSaleAttrValueMapper, PmsSkuSaleAttrValue> implements PmsSkuSaleAttrValueService {

    @Autowired
    PmsSkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Override
    public List<String> getSkuSaleAttrValuesAsString(Long skuId) {
        return skuSaleAttrValueMapper.getSkuSaleAttrValuesAsString(skuId);
    }
}
