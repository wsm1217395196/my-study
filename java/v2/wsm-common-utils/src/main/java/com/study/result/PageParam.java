package com.study.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页参数类
 */
@Getter
@Setter
@ToString
@ApiModel(value = "PageParam对象", description = "分页参数类")
public class PageParam {
    /**
     * 要查第几页？
     */
    @ApiModelProperty(value = "要查第几页？")
    private int pageIndex;
    /**
     * 要查一页多少数据？
     */
    @ApiModelProperty(value = "要查一页多少数据？")
    private int pageSize;
    /**
     * 要查的字段（注意：别名对应model属性名）
     */
    @ApiModelProperty(value = "要查的字段（注意：别名对应model属性名）")
    private String sqlColumns;
    /**
     * 排序字段（如 update_time-desc,name-asc）
     */
    @ApiModelProperty(value = "排序字段（如 update_time-desc,name-asc）")
    private String sort;
    /**
     * 条件(json格式："{'name':'wsm','isEnable':'1'}")
     */
    @ApiModelProperty(value = "条件(json格式：\"{'name':'wsm','isEnable':'1'}\")")
    private String condition;

    public PageParam() {
    }

    public PageParam(int pageIndex, int pageSize, String sort, String condition) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.sort = sort;
        this.condition = condition;
    }

    public int getPageStart() {
        if (pageIndex > 0) {
            return (pageIndex - 1) * pageSize;
        }
        return pageIndex;
    }

}
