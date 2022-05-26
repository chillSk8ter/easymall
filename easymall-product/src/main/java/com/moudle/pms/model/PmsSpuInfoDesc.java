package com.moudle.pms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * spu信息介绍
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("pms_spu_info_desc")
@ApiModel(value="PmsSpuInfoDesc对象", description="spu信息介绍")
public class PmsSpuInfoDesc implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "商品id")
        private Long spuId;

      @ApiModelProperty(value = "商品介绍")
      private String decript;


}
