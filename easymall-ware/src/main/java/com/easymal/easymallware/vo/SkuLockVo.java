package com.easymal.easymallware.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Name: SkuLockVo
 * @Author peipei
 * @Date 2022/5/2
 */
@Data
public class SkuLockVo {
    private Integer count;

    private List<Long> wareIds;

    private Long skuId;

}
