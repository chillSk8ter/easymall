package com.moudle.pms.mapper;

import com.moudle.pms.model.PmsAttrGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 属性分组 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsAttrGroupMapper extends BaseMapper<PmsAttrGroup> {

    List<PmsAttrGroup> queryPage(@Param("key") String key, @Param("catelogId") long catelogId);
}
