package com.moudle.pms.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.moudle.pms.model.PmsAttr;
import com.moudle.pms.model.PmsAttrGroup;
import com.moudle.pms.model.PmsCategoryBrandRelation;
import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Name: PmsCateGroupWithAttrVo
 * @Author peipei
 * @Date 2022/3/30
 */
@Data
public class PmsCateGroupWithAttrVo extends PmsAttrGroup {
    @TableField(exist = false)
    List<PmsAttr> pmsAttrList;
}
