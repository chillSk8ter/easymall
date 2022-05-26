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
 * 专题商品
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("sms_home_subject_spu")
@ApiModel(value="SmsHomeSubjectSpu对象", description="专题商品")
public class SmsHomeSubjectSpu implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "专题名字")
      private String name;

      @ApiModelProperty(value = "专题id")
      private Long subjectId;

      @ApiModelProperty(value = "spu_id")
      private Long spuId;

      @ApiModelProperty(value = "排序")
      private Integer sort;


}
