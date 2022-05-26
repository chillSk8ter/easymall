package com.easymall.easymallcoupon.sms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 秒杀商品通知订阅
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("sms_seckill_sku_notice")
@ApiModel(value="SmsSeckillSkuNotice对象", description="秒杀商品通知订阅")
public class SmsSeckillSkuNotice implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "member_id")
      private Long memberId;

      @ApiModelProperty(value = "sku_id")
      private Long skuId;

      @ApiModelProperty(value = "活动场次id")
      private Long sessionId;

      @ApiModelProperty(value = "订阅时间")
      private Date subcribeTime;

      @ApiModelProperty(value = "发送时间")
      private Date sendTime;

      @ApiModelProperty(value = "通知方式[0-短信，1-邮件]")
      private Boolean noticeType;


}
