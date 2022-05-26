package com.moudle.pms.service;

import com.moudle.pms.model.PmsBrand;
import com.moudle.pms.model.PmsCategoryBrandRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌分类关联 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsCategoryBrandRelationService extends IService<PmsCategoryBrandRelation> {


    /**
     * 根据品牌id查询商品的分类
     * @param brandId
     * @return
     */
    List<PmsCategoryBrandRelation> catelogList(Long brandId);

    /**
     * 新增品牌分类
     * @param pmsCategoryBrandRelation
     */
    void saveDetail(PmsCategoryBrandRelation pmsCategoryBrandRelation);

    /**
     * 更新品牌-分类级联
     * @param brandId
     * @param name
     */
    void updateBrand(Long brandId, String name);

    /**
     * 更新品牌-分类级联
     * @param catId
     * @param name
     */
    void updateCate(Long catId, String name);

    /**
     *通过分类id查询品牌实体
     * @param catId
     * @return
     */
    List<PmsBrand> getBrandByCateId(Long catId);

    List<PmsCategoryBrandRelation> listByBrandId(Long brandId);
}
