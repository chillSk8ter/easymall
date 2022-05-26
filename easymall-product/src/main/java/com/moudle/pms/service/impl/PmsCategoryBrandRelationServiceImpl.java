package com.moudle.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moudle.pms.mapper.PmsCategoryBrandRelationMapper;
import com.moudle.pms.model.PmsBrand;
import com.moudle.pms.model.PmsCategory;
import com.moudle.pms.model.PmsCategoryBrandRelation;
import com.moudle.pms.service.PmsBrandService;
import com.moudle.pms.service.PmsCategoryBrandRelationService;
import com.moudle.pms.service.PmsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 品牌分类关联 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Service
public class PmsCategoryBrandRelationServiceImpl extends ServiceImpl<PmsCategoryBrandRelationMapper, PmsCategoryBrandRelation> implements PmsCategoryBrandRelationService {
    @Autowired
    PmsCategoryService pmsCategoryService;

    @Autowired
    PmsBrandService pmsBrandService;

    @Autowired
    PmsCategoryBrandRelationMapper categoryBrandRelationMapper;

    @Override
    public List<PmsCategoryBrandRelation> catelogList(Long brandId) {
        LambdaQueryWrapper<PmsCategoryBrandRelation> lambdaQueryWrapper = new QueryWrapper<PmsCategoryBrandRelation>().lambda();
        lambdaQueryWrapper.eq(PmsCategoryBrandRelation::getBrandId, brandId);
        return list(lambdaQueryWrapper);
    }

    @Override
    public void saveDetail(PmsCategoryBrandRelation pmsCategoryBrandRelation) {
        PmsCategory pmsCategory = pmsCategoryService.getById(pmsCategoryBrandRelation.getId());
        PmsBrand pmsBrand = pmsBrandService.getById(pmsCategoryBrandRelation.getBrandId());
        pmsCategoryBrandRelation.setBrandName(pmsBrand.getName());
        pmsCategoryBrandRelation.setCatelogName(pmsCategory.getName());
        save(pmsCategoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        categoryBrandRelationMapper.updateBrand(brandId,name);
    }

    @Override
    public void updateCate(Long catId, String name) {
        categoryBrandRelationMapper.updateCate(catId,name);
    }

    @Override
    public List<PmsBrand> getBrandByCateId(Long catId) {
        LambdaQueryWrapper<PmsCategoryBrandRelation> lambdaQueryWrapper = new QueryWrapper<PmsCategoryBrandRelation>().lambda();
        lambdaQueryWrapper.eq(PmsCategoryBrandRelation::getCatelogId,catId);
        List<PmsCategoryBrandRelation> categoryBrandRelations = list(lambdaQueryWrapper);
        List<Long> brandIds = categoryBrandRelations.stream()
                .map(PmsCategoryBrandRelation::getBrandId).collect(Collectors.toList());

        List<PmsBrand> pmsBrands = pmsBrandService.listByIds(brandIds);
        return pmsBrands;
    }

    @Override
    public List<PmsCategoryBrandRelation> listByBrandId(Long brandId) {
        LambdaQueryWrapper<PmsCategoryBrandRelation> lambdaQueryWrapper = new QueryWrapper<PmsCategoryBrandRelation>().lambda();
        lambdaQueryWrapper.eq(PmsCategoryBrandRelation::getBrandId,brandId);
        return list(lambdaQueryWrapper);
    }
}
