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
 * 属性&属性分组关联
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("pms_attr_attrgroup_relation")
@ApiModel(value="PmsAttrAttrgroupRelation对象", description="属性&属性分组关联")
public class PmsAttrAttrgroupRelation implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "属性id")
      private Long attrId;

      @ApiModelProperty(value = "属性分组id")
      private Long attrGroupId;

      @ApiModelProperty(value = "属性组内排序")
      private Integer attrSort;


}
