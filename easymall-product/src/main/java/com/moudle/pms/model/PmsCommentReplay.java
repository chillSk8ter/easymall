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
 * 商品评价回复关系
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("pms_comment_replay")
@ApiModel(value="PmsCommentReplay对象", description="商品评价回复关系")
public class PmsCommentReplay implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "评论id")
      private Long commentId;

      @ApiModelProperty(value = "回复id")
      private Long replyId;


}
