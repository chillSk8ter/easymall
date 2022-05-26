package com.moudle.pms.service;

import com.moudle.pms.model.PmsProductAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsProductAttrValueService extends IService<PmsProductAttrValue> {

    List<PmsProductAttrValue> selectBySpuId(String spuId);
}
