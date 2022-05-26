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
 * 秒杀活动
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("sms_seckill_promotion")
@ApiModel(value="SmsSeckillPromotion对象", description="秒杀活动")
public class SmsSeckillPromotion implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "活动标题")
      private String title;

      @ApiModelProperty(value = "开始日期")
      private Date startTime;

      @ApiModelProperty(value = "结束日期")
      private Date endTime;

      @ApiModelProperty(value = "上下线状态")
      private Integer status;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

      @ApiModelProperty(value = "创建人")
      private Long userId;


}
