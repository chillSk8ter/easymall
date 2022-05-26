package com.easymall.easymallcoupon.sms.model;

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
 * 优惠券分类关联
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("sms_coupon_spu_category_relation")
@ApiModel(value="SmsCouponSpuCategoryRelation对象", description="优惠券分类关联")
public class SmsCouponSpuCategoryRelation implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "优惠券id")
      private Long couponId;

      @ApiModelProperty(value = "产品分类id")
      private Long categoryId;

      @ApiModelProperty(value = "产品分类名称")
      private String categoryName;


}
