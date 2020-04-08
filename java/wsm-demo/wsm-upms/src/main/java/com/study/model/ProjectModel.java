package com.study.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 项目（如公司里某个项目）
 * </p>
 *
 * @author wsm
 * @since 2019-10-24
 */
@Getter
@Setter
@ToString
@TableName("project")
@ApiModel(value="ProjectModel对象", description="项目（如公司里某个项目）")
public class ProjectModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "域id")
    private Long regionId;

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
