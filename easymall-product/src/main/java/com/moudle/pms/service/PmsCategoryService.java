package com.moudle.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moudle.pms.model.PmsCategory;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsCategoryService extends IService<PmsCategory> {


    /**
     *获取商品分类树形菜单
     * @return
     */
    List<PmsCategory> getListTree();


    /**
     * 批量删除商品分类
     * @param cateIds
     */
    void deleteIds(Long[] cateIds);

    /**
     * 获取商品分类信息
     * @param catId
     * @return
     */
    PmsCategory getCateInfo(String catId);

    /**
     * 更新
     * @param pmsCategory
     */
    void updateCategoty(PmsCategory pmsCategory);

    /**
     * 获取分类路径
     * @param catelogId
     * @return
     */
    List<Long> getCateLogPathById(Long catelogId);


    /**
     * 获取一级分类菜单
     * @return
     */
    List<PmsCategory> getLevel1Catagories();

}
