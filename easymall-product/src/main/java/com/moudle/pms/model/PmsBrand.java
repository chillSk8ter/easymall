package com.moudle.pms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 品牌
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_brand")
@ApiModel(value = "PmsBrand对象", description = "品牌")
public class PmsBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "品牌id")
    @TableId(value = "brand_id", type = IdType.AUTO)
    @NotNull(message = "品牌id不能为null")
    private Long brandId;

    @ApiModelProperty(value = "品牌名")
    @NotBlank(message = "品牌名不能为空")
    private String name;

    @ApiModelProperty(value = "品牌logo地址")
    private String logo;

    @ApiModelProperty(value = "介绍")
    private String descript;

    @ApiModelProperty(value = "显示状态[0-不显示；1-显示]")
    private Integer showStatus;

    @ApiModelProperty(value = "检索首字母")
    private String firstLetter;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
