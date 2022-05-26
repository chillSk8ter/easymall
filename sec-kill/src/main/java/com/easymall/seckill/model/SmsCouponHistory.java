package com.easymall.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 优惠券领取历史记录
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_coupon_history")
@ApiModel(value = "SmsCouponHistory对象", description = "优惠券领取历史记录")
public class SmsCouponHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "优惠券id")
    private Long couponId;

    @ApiModelProperty(value = "会员id")
    private Long memberId;

    @ApiModelProperty(value = "会员名字")
    private String memberNickName;

    @ApiModelProperty(value = "获取方式[0->后台赠送；1->主动领取]")
    private Boolean getType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "使用状态[0->未使用；1->已使用；2->已过期]")
    private Boolean useType;

    @ApiModelProperty(value = "使用时间")
    private Date useTime;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    private Long orderSn;


}
