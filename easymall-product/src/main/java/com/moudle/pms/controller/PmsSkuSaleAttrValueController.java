package com.moudle.pms.controller;


import com.moudle.pms.service.PmsSkuSaleAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * sku销售属性&值 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/pms/pmsSkuSaleAttrValue")
public class PmsSkuSaleAttrValueController {
    @Autowired
    PmsSkuSaleAttrValueService skuSaleAttrValueService;


    /**
     * 列表
     */
    @RequestMapping("/getSkuSaleAttrValuesAsString")
    public List<String> getSkuSaleAttrValuesAsString(@RequestBody Long skuId) {
        return skuSaleAttrValueService.getSkuSaleAttrValuesAsString(skuId);
    }


}

