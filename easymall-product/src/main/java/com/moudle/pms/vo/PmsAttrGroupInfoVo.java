package com.moudle.pms.vo;

import com.moudle.pms.model.PmsAttrGroup;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Name: PmsAttrGroupInfoVo
 * @Author peipei
 * @Date 2022/3/27
 */
@Data
public class PmsAttrGroupInfoVo extends PmsAttrGroup {
    private Long catelogId;

    private List<Long> catelogPath;

}
