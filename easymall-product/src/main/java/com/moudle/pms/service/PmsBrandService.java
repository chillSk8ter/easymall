package com.moudle.pms.service;

import com.moudle.pms.model.PmsBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import easymall.easymallcommon.utils.PageUtils;

import java.util.Map;

/**
 * <p>
 * 品牌 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsBrandService extends IService<PmsBrand> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     *更新商品-分类级联
     * @param pmsBrand
     */
    void updateCascade(PmsBrand pmsBrand);
}
