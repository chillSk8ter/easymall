package com.easymall.search.controller;

import com.easymall.search.service.ProductService;
import easymall.easymallcommon.exception.BizCodeEnum;
import easymall.easymallcommon.to.es.SkuEsModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Name: ElasticSaveController
 * @Author peipei
 * @Date 2022/4/7
 */
@Slf4j
@RequestMapping("/")
@RestController
public class ElasticSaveController {
    @Autowired
    ProductService productService;

    @PostMapping("/product")
    public R saveProductAsIndices(@RequestBody List<SkuEsModel> skuEsModelList) {
        boolean status = false;
        try {
            status = productService.saveProductAsIndices(skuEsModelList);
        } catch (IOException e) {
            log.error("远程调用服务失败");
        }
        if (status) {
            return R.ok();
        } else {
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTION.getMsg());
        }
    }

}
