package com.moudle.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moudle.pms.model.PmsProductAttrValue;
import com.moudle.pms.mapper.PmsProductAttrValueMapper;
import com.moudle.pms.service.PmsProductAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * spu属性值 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Service
public class PmsProductAttrValueServiceImpl extends ServiceImpl<PmsProductAttrValueMapper, PmsProductAttrValue> implements PmsProductAttrValueService {

    @Override
    public List<PmsProductAttrValue> selectBySpuId(String spuId) {
        LambdaQueryWrapper<PmsProductAttrValue> wrapper = new QueryWrapper<PmsProductAttrValue>().lambda();
        wrapper.eq(PmsProductAttrValue::getSpuId,spuId);
        return list(wrapper);
    }
}
