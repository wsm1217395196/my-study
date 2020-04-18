package com.study.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value="UserAddDTO", description="用户新增业务对象")
public class UserAddDTO {

    private Long id;

    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度为 6-20 位")
    @ApiModelProperty(value = "密码")
    private String password;

    @Range(min = 0, max = 1, message = "性别选择错误")
    @ApiModelProperty(value = "性别(0女,1男)")
    private Integer sex;

    @Range(min = 1, max = 3, message = "用户状态选择错误")
    @ApiModelProperty(value = "用户状态（1：正常，2：异常，3：禁用）")
    private Integer status;

}
