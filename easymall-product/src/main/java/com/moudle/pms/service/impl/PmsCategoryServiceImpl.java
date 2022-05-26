package com.moudle.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moudle.pms.model.PmsCategory;
import com.moudle.pms.mapper.PmsCategoryMapper;
import com.moudle.pms.service.PmsCategoryBrandRelationService;
import com.moudle.pms.service.PmsCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Service
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements PmsCategoryService {
    @Autowired
    PmsCategoryBrandRelationService categoryBrandRelationService;


    @Override
    public List<PmsCategory> getListTree() {
        List<PmsCategory> categoryList = list();
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getParentCid() == 0) {
                PmsCategory parentCate = categoryList.get(i);
                parentCate.setChildren(getChildCate(parentCate, categoryList));
            }
        }
        categoryList.sort(new Comparator<PmsCategory>() {
            @Override
            public int compare(PmsCategory p1, PmsCategory p2) {
                return p1.getSort() - p2.getSort();
            }
        });
        return categoryList;
    }

    @Override
    public void deleteIds(Long[] cateIds) {
        removeByIds(Arrays.asList(cateIds));
    }

    @Override
    public PmsCategory getCateInfo(String catId) {
        LambdaQueryWrapper<PmsCategory> lambda = new QueryWrapper<PmsCategory>().lambda();
        lambda.eq(PmsCategory::getCatId, catId);
        return getOne(lambda);
    }

    @Transactional
    @Override
    public void updateCategoty(PmsCategory pmsCategory) {
        updateById(pmsCategory);
        categoryBrandRelationService.updateCate(pmsCategory.getCatId(), pmsCategory.getName());
    }

    @Override
    public List<Long> getCateLogPathById(Long catelogId) {
        List<Long> path = new ArrayList<>();
        getPath(catelogId, path);
        Collections.reverse(path);
        return path;
    }

    @Cacheable({"category"})
    @Override
    public List<PmsCategory> getLevel1Catagories() {
        LambdaQueryWrapper<PmsCategory> wrapper = new QueryWrapper<PmsCategory>().lambda();
        wrapper.eq(PmsCategory::getParentCid, 0);
        return list(wrapper);
    }

    private void getPath(Long catelogId, List<Long> path) {
        if (catelogId != 0) {
            path.add(catelogId);
            PmsCategory parentCate = getById(catelogId);
            getPath(parentCate.getParentCid(), path);
        }
    }

    public List<PmsCategory> getChildCate(PmsCategory parentCate, List<PmsCategory> categoryList) {
        List<PmsCategory> childCateList = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getParentCid().equals(parentCate.getCatId())) {
                PmsCategory childCate = categoryList.get(i);
                childCate.setChildren(getChildCate(childCate, categoryList));
                childCateList.add(childCate);
            }
        }
        childCateList.sort(new Comparator<PmsCategory>() {
            @Override
            public int compare(PmsCategory p1, PmsCategory p2) {
                return p1.getSort() - p2.getSort();
            }
        });
        return childCateList;
    }
}
