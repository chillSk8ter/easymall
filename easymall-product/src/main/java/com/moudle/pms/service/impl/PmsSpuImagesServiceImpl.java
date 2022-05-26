package com.moudle.pms.service.impl;

import com.moudle.pms.model.PmsSpuImages;
import com.moudle.pms.mapper.PmsSpuImagesMapper;
import com.moudle.pms.service.PmsSpuImagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * spu图片 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Service
public class PmsSpuImagesServiceImpl extends ServiceImpl<PmsSpuImagesMapper, PmsSpuImages> implements PmsSpuImagesService {

    @Override
    public void saveImages(Long spuId, List<String> images) {
        if (images == null) {
            return;
        }
        ArrayList<PmsSpuImages> pmsSpuImagesList = new ArrayList<>();
        for (String image : images) {
            PmsSpuImages pmsSpuImages = new PmsSpuImages();
            pmsSpuImages.setSpuId(spuId);
            pmsSpuImages.setImgUrl(image);
            pmsSpuImagesList.add(pmsSpuImages);
        }
        saveBatch(pmsSpuImagesList);
    }
}
