package com.moudle.pms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moudle.pms.model.PmsSkuInfo;
import com.moudle.pms.mapper.PmsSkuInfoMapper;
import com.moudle.pms.model.PmsSpuInfo;
import com.moudle.pms.service.PmsSkuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moudle.pms.service.PmsSpuInfoService;
import easymall.easymallcommon.utils.PageUtils;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Service
public class PmsSkuInfoServiceImpl extends ServiceImpl<PmsSkuInfoMapper, PmsSkuInfo> implements PmsSkuInfoService {

    @Autowired
    PmsSpuInfoService spuInfoService;

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        IPage<PmsSkuInfo> resPage = new Page<>((Long) params.get("page"), (Long) params.get("limit"));
        LambdaQueryWrapper<PmsSkuInfo> wrapper = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");
        Long catelogId = (Long) params.get("catelogId");
        Long brandId = (Long) params.get("brandId");
        Integer min = (Integer) params.get("min");
        Integer max = (Integer) params.get("max");
        if (key != null) {
            wrapper.eq(PmsSkuInfo::getSkuName, key);
        }
        if (catelogId != null) {
            wrapper.eq(PmsSkuInfo::getCatalogId, catelogId);
        }
        if (brandId != null) {
            wrapper.eq(PmsSkuInfo::getBrandId, brandId);
        }
        if (min != null) {
            BigDecimal minDec = new BigDecimal(min);
            if (minDec.compareTo(new BigDecimal(0)) == 1) {
                wrapper.ge(PmsSkuInfo::getPrice, minDec);
            }
        }
        if (min != null) {
            BigDecimal maxDec = new BigDecimal(max);
            if (maxDec.compareTo(new BigDecimal(0)) == 1) {
                wrapper.le(PmsSkuInfo::getPrice, maxDec);
            }
        }
        IPage<PmsSkuInfo> page = page(resPage, wrapper);
        return new PageUtils(page);
    }


    @Override
    public PmsSpuInfo getSpuBySkuId(Long skuId) {
        return spuInfoService.getById(skuId);
    }

    @Override
    public List<PmsSkuInfo> getSkuBySpuId(String spuId) {
        LambdaQueryWrapper<PmsSkuInfo> wrapper = new QueryWrapper<PmsSkuInfo>().lambda();
        wrapper.eq(PmsSkuInfo::getSpuId, spuId);
        return list(wrapper);
    }
}
