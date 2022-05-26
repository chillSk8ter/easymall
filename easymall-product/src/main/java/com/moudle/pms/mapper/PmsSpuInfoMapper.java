package com.moudle.pms.mapper;

import com.moudle.pms.model.PmsSpuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * spu信息 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsSpuInfoMapper extends BaseMapper<PmsSpuInfo> {

    void upSpuStatus(@Param("spuId") String spuId, @Param("code") int code);

}
