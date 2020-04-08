package com.study.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通用BaseDto
 */
@Getter
@Setter
@ToString
@ApiModel(value = "BaseDto对象", description = "通用BaseDto")
public class BaseDto {

    private Long id;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

}
