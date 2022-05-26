package com.moudle.pms.mapper;

import com.moudle.pms.model.PmsSkuSaleAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * sku销售属性&值 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsSkuSaleAttrValueMapper extends BaseMapper<PmsSkuSaleAttrValue> {

    List<String> getSkuSaleAttrValuesAsString(Long skuId);
}
