package com.moudle.pms.model;

import java.math.BigDecimal;
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
 * spu信息
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("pms_spu_info")
@ApiModel(value="PmsSpuInfo对象", description="spu信息")
public class PmsSpuInfo implements Serializable {

    private static final long serialVersionUID=1L;

      @ApiModelProperty(value = "商品id")
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "商品名称")
      private String spuName;

      @ApiModelProperty(value = "商品描述")
      private String spuDescription;

      @ApiModelProperty(value = "所属分类id")
      private Long catalogId;

      @ApiModelProperty(value = "品牌id")
      private Long brandId;

    private BigDecimal weight;

      @ApiModelProperty(value = "上架状态[0 - 下架，1 - 上架]")
      private Integer publishStatus;

    private Date createTime;

    private Date updateTime;


}
