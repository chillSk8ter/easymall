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
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("sms_home_subject")
@ApiModel(value="SmsHomeSubject对象", description="首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】")
public class SmsHomeSubject implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "专题名字")
      private String name;

      @ApiModelProperty(value = "专题标题")
      private String title;

      @ApiModelProperty(value = "专题副标题")
      private String subTitle;

      @ApiModelProperty(value = "显示状态")
      private Boolean status;

      @ApiModelProperty(value = "详情连接")
      private String url;

      @ApiModelProperty(value = "排序")
      private Integer sort;

      @ApiModelProperty(value = "专题图片地址")
      private String img;


}
