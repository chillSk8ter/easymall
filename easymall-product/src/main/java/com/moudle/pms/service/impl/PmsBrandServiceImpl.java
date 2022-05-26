package com.moudle.pms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moudle.pms.model.PmsBrand;
import com.moudle.pms.mapper.PmsBrandMapper;
import com.moudle.pms.service.PmsBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moudle.pms.service.PmsCategoryBrandRelationService;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 品牌 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Service
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandMapper, PmsBrand> implements PmsBrandService {
    @Autowired
    PmsBrandMapper pmsBrandMapper;

    @Autowired
    PmsCategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        LambdaQueryWrapper<PmsBrand> queryWrapper = new QueryWrapper<PmsBrand>().lambda();
        if (StrUtil.isNotBlank(key)) {
            queryWrapper.eq(PmsBrand::getBrandId, key).or().like(PmsBrand::getName, key);
        }
        Page<PmsBrand> brandPage = new Page<>();
        return new PageUtils(page(brandPage, queryWrapper));
    }

    @Transactional
    @Override
    public void updateCascade(PmsBrand pmsBrand) {
        updateById(pmsBrand);
        categoryBrandRelationService.updateBrand(pmsBrand.getBrandId(), pmsBrand.getName());
    }
}
