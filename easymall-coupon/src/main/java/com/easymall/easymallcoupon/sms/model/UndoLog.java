package com.easymall.easymallcoupon.sms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    @TableName("undo_log")
@ApiModel(value="UndoLog对象", description="")
public class UndoLog implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

    private Long branchId;

    private String xid;

    private String context;

    private Blob rollbackInfo;

    private Integer logStatus;

    private Date logCreated;

    private Date logModified;

    private String ext;


}
