package com.moudle.pms.service;

import com.moudle.pms.model.PmsSkuSaleAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku销售属性&值 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsSkuSaleAttrValueService extends IService<PmsSkuSaleAttrValue> {

    /**
     * 获取销售属性列表
     * @param skuId
     * @return
     */
    List<String> getSkuSaleAttrValuesAsString(Long skuId);
}
