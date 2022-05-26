package com.easymall.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品满减信息
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("sms_sku_full_reduction")
@ApiModel(value="SmsSkuFullReduction对象", description="商品满减信息")
public class SmsSkuFullReduction implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "spu_id")
      private Long skuId;

      @ApiModelProperty(value = "满多少")
      private BigDecimal fullPrice;

      @ApiModelProperty(value = "减多少")
      private BigDecimal reducePrice;

      @ApiModelProperty(value = "是否参与其他优惠")
      private Boolean addOther;


}
