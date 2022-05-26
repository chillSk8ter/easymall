package com.moudle.pms.service;

import com.moudle.pms.model.PmsSpuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moudle.pms.vo.SpuSaveVo;
import easymall.easymallcommon.utils.PageUtils;

import java.util.HashMap;

/**
 * <p>
 * spu信息 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsSpuInfoService extends IService<PmsSpuInfo> {

    /**
     * 根据参数查找商品spu
     * @param params
     * @return
     */
    PageUtils queryPageByCondition(HashMap<String, Object> params);

    /**
     * 保存商品spu信息
     * @param spuSaveVo
     */
    void saveSpuInfo(SpuSaveVo spuSaveVo);

    void upSpuForSearch(String spuId);
}
