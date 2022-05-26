package com.moudle.pms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 品牌分类关联
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_category_brand_relation")
@ApiModel(value = "PmsCategoryBrandRelation对象", description = "品牌分类关联")
public class PmsCategoryBrandRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "分类id")
    private Long catelogId;

    private String brandName;

    private String catelogName;


}
