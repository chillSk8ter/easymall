package com.moudle.pms.model;

import java.math.BigDecimal;
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
 * sku信息
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("pms_sku_info")
@ApiModel(value="PmsSkuInfo对象", description="sku信息")
public class PmsSkuInfo implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "skuId")
        @TableId(value = "sku_id", type = IdType.AUTO)
      private Long skuId;

      @ApiModelProperty(value = "spuId")
      private Long spuId;

      @ApiModelProperty(value = "sku名称")
      private String skuName;

      @ApiModelProperty(value = "sku介绍描述")
      private String skuDesc;

      @ApiModelProperty(value = "所属分类id")
      private Long catalogId;

      @ApiModelProperty(value = "品牌id")
      private Long brandId;

      @ApiModelProperty(value = "默认图片")
      private String skuDefaultImg;

      @ApiModelProperty(value = "标题")
      private String skuTitle;

      @ApiModelProperty(value = "副标题")
      private String skuSubtitle;

      @ApiModelProperty(value = "价格")
      private BigDecimal price;

      @ApiModelProperty(value = "销量")
      private Long saleCount;


}
