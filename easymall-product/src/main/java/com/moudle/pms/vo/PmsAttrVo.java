package com.moudle.pms.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.moudle.pms.model.PmsAttr;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Name: PmsAttrVo
 * @Author peipei
 * @Date 2022/3/25
 */
@Data
public class PmsAttrVo extends PmsAttr {

    private Long attrGroupId;

}
