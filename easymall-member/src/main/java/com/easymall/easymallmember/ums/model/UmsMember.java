package com.easymall.easymallmember.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员
 * </p>
 *
 * @author peipei
 * @since 2022-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_member")
@ApiModel(value = "UmsMember对象", description = "会员")
public class UmsMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "会员等级id")
    private Long levelId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String header;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "生日")
    private Date birth;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "职业")
    private String job;

    @ApiModelProperty(value = "个性签名")
    private String sign;

    @ApiModelProperty(value = "用户来源")
    private Integer sourceType;

    @ApiModelProperty(value = "积分")
    private Integer integration;

    @ApiModelProperty(value = "成长值")
    private Integer growth;

    @ApiModelProperty(value = "启用状态")
    private Integer status;

    @ApiModelProperty(value = "注册时间")
    private Date createTime;

    @ApiModelProperty(value = "社交账号ID")
    private String socialUid;

    @ApiModelProperty(value = "社交账号Token")
    private String accessToken;

    @ApiModelProperty(value = "社交账号Token过期时间")
    private String expiresIn;


}
