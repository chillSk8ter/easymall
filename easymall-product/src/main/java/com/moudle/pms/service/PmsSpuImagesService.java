package com.moudle.pms.service;

import com.moudle.pms.model.PmsSpuImages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu图片 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsSpuImagesService extends IService<PmsSpuImages> {
    /**
     * 保存spu图片信息
     * @param id
     * @param images
     */
    void saveImages(Long id, List<String> images);
}
