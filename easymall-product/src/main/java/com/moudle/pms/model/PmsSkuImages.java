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
 * sku图片
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("pms_sku_images")
@ApiModel(value="PmsSkuImages对象", description="sku图片")
public class PmsSkuImages implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "sku_id")
      private Long skuId;

      @ApiModelProperty(value = "图片地址")
      private String imgUrl;

      @ApiModelProperty(value = "排序")
      private Integer imgSort;

      @ApiModelProperty(value = "默认图[0 - 不是默认图，1 - 是默认图]")
      private Integer defaultImg;


}
