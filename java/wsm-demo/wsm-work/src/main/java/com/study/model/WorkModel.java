package com.study.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 职业信息表
 * </p>
 *
 * @author wsm
 * @since 2019-10-24
 */
@Getter
@Setter
@ToString
@TableName("work")
@ApiModel(value="WorkModel对象", description="职业信息表")
public class WorkModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "招聘平台id")
    private Long recruitPlatformId;

    @ApiModelProperty(value = "职位id")
    private Long jobId;

    @ApiModelProperty(value = "地点")
    private String site;

    @ApiModelProperty(value = "日期")
    private LocalDate date;

    @ApiModelProperty(value = "条件")
    @TableField("`condition`")
    private String condition;

    @ApiModelProperty(value = "职位条数")
    private Integer jobNumber;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否有效(0无效，1有效)")
    private Integer isEnabled;

    @ApiModelProperty(value = "备注")
    private String remark;


}
