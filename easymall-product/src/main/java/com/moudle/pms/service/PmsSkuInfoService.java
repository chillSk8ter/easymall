package com.moudle.pms.service;

import com.moudle.pms.model.PmsSkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moudle.pms.model.PmsSpuInfo;
import easymall.easymallcommon.utils.PageUtils;
import org.omg.PortableInterceptor.ServerRequestInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsSkuInfoService extends IService<PmsSkuInfo> {

    /**
     * 检索商品sku
     * @param params
     * @return
     */
    PageUtils queryPageByCondition(Map<String, Object> params);



    List<PmsSkuInfo> getSkuBySpuId(String spuId);


    PmsSpuInfo getSpuBySkuId(Long skuId);

}
