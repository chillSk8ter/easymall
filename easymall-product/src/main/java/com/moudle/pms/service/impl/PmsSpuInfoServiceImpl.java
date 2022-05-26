package com.moudle.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moudle.pms.feign.CouponFeignService;
import com.moudle.pms.feign.SearchFeignService;
import com.moudle.pms.feign.WareFeignService;
import com.moudle.pms.mapper.PmsSpuInfoMapper;
import com.moudle.pms.model.*;
import com.moudle.pms.service.*;
import com.moudle.pms.vo.*;
import easymall.easymallcommon.constant.ProductConstant;
import easymall.easymallcommon.constant.QueryConstant;
import easymall.easymallcommon.to.SkuHasStockVo;
import easymall.easymallcommon.to.SkuReductionTo;
import easymall.easymallcommon.to.es.SkuEsModel;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * spu信息 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Service
public class PmsSpuInfoServiceImpl extends ServiceImpl<PmsSpuInfoMapper, PmsSpuInfo> implements PmsSpuInfoService {
    @Autowired
    PmsSpuInfoDescService pmsSpuInfoDescService;

    @Autowired
    PmsBrandService pmsBrandService;

    @Autowired
    PmsSpuImagesService pmsSpuImagesService;

    @Autowired
    PmsProductAttrValueService productAttrValueService;

    @Autowired
    PmsAttrService pmsAttrService;

    @Autowired
    PmsSkuInfoService skuInfoService;

    @Autowired
    PmsSkuImagesService skuImagesService;

    @Autowired
    PmsSkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    PmsCategoryService pmsCategoryService;

    @Autowired
    CouponFeignService couponFeignService;

    @Autowired
    WareFeignService wareFeignService;

    @Autowired
    SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPageByCondition(HashMap<String, Object> params) {
        IPage<PmsSpuInfo> pmsSpuInfoPage = new Page<>((Long) params.get("page"), (Long) params.get("limit"));
        QueryWrapper<PmsSpuInfo> wrapper = new QueryWrapper<>();
        if (params.get("brandId") != null) {
            wrapper.eq("brandId", (Long) params.get("brandId"));
        }
        if (params.get("key") != null) {
            wrapper.eq("key", params.get("key"));
        }
        if (params.get("sidx") != null) {
            String sidx = (String) params.get("sidx");
            String order = (String) params.get("order");
            if (order.equals(QueryConstant.Order.ASC.getOrder())) {
                wrapper.orderByAsc(sidx);
            } else {
                wrapper.orderByDesc(sidx);
            }
        }
        wrapper.eq("status", params.get("status"));
        return new PageUtils(page(pmsSpuInfoPage, wrapper));
    }

    @Transactional
    @Override
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {
        //1、保存spu基本信息 pms_spu_info
        PmsSpuInfo pmsSpuInfo = new PmsSpuInfo();
        BeanUtils.copyProperties(spuSaveVo, pmsSpuInfo);
        pmsSpuInfo.setCreateTime(new Date());
        pmsSpuInfo.setUpdateTime(new Date());
        save(pmsSpuInfo);

        // 2、保存Spu的描述图片 pms_spu_info_desc
        PmsSpuInfoDesc pmsSpuInfoDesc = new PmsSpuInfoDesc();
        String decript = String.join(",", spuSaveVo.getDecript());
        pmsSpuInfoDesc.setSpuId(pmsSpuInfo.getId());
        pmsSpuInfoDesc.setDecript(decript);
        pmsSpuInfoDescService.save(pmsSpuInfoDesc);

        //3、保存spu的图片集 pms_spu_images
        List<String> images = spuSaveVo.getImages();
        pmsSpuImagesService.saveImages(pmsSpuInfo.getId(), images);

        //4、保存spu的规格参数;pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
        List<PmsProductAttrValue> productAttrValueList = new ArrayList<>();
        for (BaseAttrs baseAttr : baseAttrs) {
            PmsProductAttrValue productAttrValue = new PmsProductAttrValue();
            productAttrValue.setSpuId(pmsSpuInfo.getId());
            productAttrValue.setAttrId(baseAttr.getAttrId());
            PmsAttr pmsAttrById = pmsAttrService.getById(baseAttr.getAttrId());
            productAttrValue.setAttrName(pmsAttrById.getAttrName());
            productAttrValue.setAttrValue(baseAttr.getAttrValues());
            productAttrValue.setQuickShow(baseAttr.getShowDesc());
            productAttrValueList.add(productAttrValue);
        }
        productAttrValueService.saveBatch(productAttrValueList);

        //5、保存当前spu对应的所有sku信息；
        List<Skus> skus = spuSaveVo.getSkus();
        ArrayList<PmsSkuInfo> skuInfoArrayList = new ArrayList<>();
        ArrayList<PmsSkuImages> skuImagesArrayList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(skus)) {
            for (Skus sku : skus) {
                PmsSkuInfo skuInfo = new PmsSkuInfo();
                BeanUtils.copyProperties(sku, skuInfo);
                skuInfo.setSpuId(pmsSpuInfo.getId());
                skuInfo.setCatalogId(pmsSpuInfo.getCatalogId());
                skuInfo.setBrandId(pmsSpuInfo.getBrandId());
                skuInfo.setSaleCount(0L);
                for (Images image : sku.getImages()) {
                    if (image.getDefaultImg() == 1) {
                        skuInfo.setSkuDefaultImg(image.getImgUrl());
                        break;
                    }
                }
                skuInfoArrayList.add(skuInfo);
                for (Images image : sku.getImages()) {
                    PmsSkuImages pmsSkuImage = new PmsSkuImages();
                    pmsSkuImage.setSkuId(skuInfo.getSkuId());
                    pmsSkuImage.setImgUrl(image.getImgUrl());
                    if (image.getDefaultImg() == 1) {
                        pmsSkuImage.setDefaultImg(image.getDefaultImg());
                    }
                    skuImagesArrayList.add(pmsSkuImage);
                }
                List<PmsSkuImages> resSkuImgList = skuImagesArrayList.stream().filter(item -> {
                    return StrUtil.isNotEmpty(item.getImgUrl());
                }).collect(Collectors.toList());
                skuImagesService.saveBatch(resSkuImgList);

                List<Attr> attrList = sku.getAttr();
                List<PmsSkuSaleAttrValue> skuSaleAttrValueList = new ArrayList<>();
                for (Attr attr : attrList) {
                    PmsSkuSaleAttrValue skuSaleAttrValue = new PmsSkuSaleAttrValue();
                    skuSaleAttrValue.setSkuId(skuInfo.getSkuId());
                    skuSaleAttrValue.setAttrId(attr.getAttrId());
                    skuSaleAttrValue.setAttrName(attr.getAttrName());
                    skuSaleAttrValue.setAttrValue(attr.getAttrName());
                    skuSaleAttrValueList.add(skuSaleAttrValue);
                }
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueList);

                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(sku, skuReductionTo);
                skuReductionTo.setSkuId(skuInfo.getSkuId());
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                    couponFeignService.saveSkuReductionTo(skuReductionTo);
                }
            }
            skuInfoService.saveBatch(skuInfoArrayList);
        }


    }

    @Override
    public void upSpuForSearch(String spuId) {
        List<PmsSkuInfo> skuBySpuId = skuInfoService.getSkuBySpuId(spuId);
        List<PmsProductAttrValue> attrValueList = productAttrValueService.selectBySpuId(spuId);
        List<Long> attrIds = attrValueList.stream().map(PmsProductAttrValue::getAttrId).collect(Collectors.toList());
        LambdaQueryWrapper<PmsAttr> wrapper = new QueryWrapper<PmsAttr>().lambda();
        wrapper.eq(PmsAttr::getSearchType, 1).in(PmsAttr::getAttrId, attrIds);
        List<PmsAttr> pmsAttrs = pmsAttrService.list(wrapper);
        List<Long> filterIds = pmsAttrs.stream().map(PmsAttr::getAttrId).collect(Collectors.toList());
        for (PmsProductAttrValue value : attrValueList) {
            if (!filterIds.contains(value.getAttrId())) {
                attrValueList.remove(value);
            }
        }
        ArrayList<SkuEsModel.Attr> esAttrs = new ArrayList<>();
        for (PmsProductAttrValue value : attrValueList) {
            SkuEsModel.Attr attr = new SkuEsModel.Attr();
            BeanUtils.copyProperties(value, attr);
            esAttrs.add(attr);
        }

        //查询sku库存
        HashMap<Long, Boolean> stockMap = new HashMap<>();
        List<Long> skuIds = skuBySpuId.stream().map(PmsSkuInfo::getSkuId).collect(Collectors.toList());
        try {
            List<SkuHasStockVo> skuStockVos = wareFeignService.getSkuStockVos(skuIds);
            for (SkuHasStockVo skuStockVo : skuStockVos) {
                stockMap.put(skuStockVo.getSkuId(), skuStockVo.getHasStock());
            }
        } catch (Exception e) {
            log.error("远程调用服务失败，原因：{}", e);
        }

        ArrayList<SkuEsModel> skuEsModels = new ArrayList<>();
        for (PmsSkuInfo skuInfo : skuBySpuId) {
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(skuInfo, skuEsModel);
            skuEsModel.setSkuPrice(skuInfo.getPrice());
            skuEsModel.setSkuImg(skuInfo.getSkuDefaultImg());
            skuEsModel.setHotScore(0L);
            PmsBrand pmsBrand = pmsBrandService.getById(skuInfo.getBrandId());
            skuEsModel.setBrandImg(pmsBrand.getLogo());
            skuEsModel.setBrandName(pmsBrand.getName());
            PmsCategory category = pmsCategoryService.getById(skuInfo.getCatalogId());
            skuEsModel.setCatalogName(category.getName());
            skuEsModel.setHasStock(stockMap.get(skuInfo.getSkuId()));
            skuEsModels.add(skuEsModel);
        }


        //TODO 5、将数据发给es进行保存：gulimall-search
        R r = searchFeignService.saveProductAsIndices(skuEsModels);
        if (r.getCode() == 0) {
            this.baseMapper.upSpuStatus(spuId, ProductConstant.ProductStatusEnum.SPU_UP.getCode());
        } else {
            log.error("商品远程es保存失败");
        }

    }
}
