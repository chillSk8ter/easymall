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
 * spu图片
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("pms_spu_images")
@ApiModel(value="PmsSpuImages对象", description="spu图片")
public class PmsSpuImages implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "spu_id")
      private Long spuId;

      @ApiModelProperty(value = "图片名")
      private String imgName;

      @ApiModelProperty(value = "图片地址")
      private String imgUrl;

      @ApiModelProperty(value = "顺序")
      private Integer imgSort;

      @ApiModelProperty(value = "是否默认图")
      private Integer defaultImg;


}
