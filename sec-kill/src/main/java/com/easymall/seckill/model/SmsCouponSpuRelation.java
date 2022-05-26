package com.easymall.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 优惠券与产品关联
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("sms_coupon_spu_relation")
@ApiModel(value="SmsCouponSpuRelation对象", description="优惠券与产品关联")
public class SmsCouponSpuRelation implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "优惠券id")
      private Long couponId;

      @ApiModelProperty(value = "spu_id")
      private Long spuId;

      @ApiModelProperty(value = "spu_name")
      private String spuName;


}
