package com.moudle.pms.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品三级分类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_category")
@ApiModel(value = "PmsCategory对象", description = "商品三级分类")
public class PmsCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类id")
    @TableId(value = "cat_id", type = IdType.AUTO)
    private Long catId;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "父分类id")
    private Long parentCid;

    @ApiModelProperty(value = "层级")
    private Integer catLevel;

    @TableLogic(value = "1", delval = "0")
    @ApiModelProperty(value = "是否显示[0-不显示，1显示]")
    private Integer showStatus;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "图标地址")
    private String icon;

    @ApiModelProperty(value = "计量单位")
    private String productUnit;

    @ApiModelProperty(value = "商品数量")
    private Integer productCount;

    /**
     * 子目录节点
     */
    @TableField(exist = false) //表示该字段不是数据库中的字段
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PmsCategory> children;


}
