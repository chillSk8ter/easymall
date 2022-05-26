package com.moudle.pms.mapper;

import com.moudle.pms.model.PmsCategoryBrandRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 品牌分类关联 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsCategoryBrandRelationMapper extends BaseMapper<PmsCategoryBrandRelation> {

    void updateBrand(@Param("brandId") Long brandId, @Param("name") String name);

    void updateCate(@Param("catId") Long catId, @Param("name") String name);
}
