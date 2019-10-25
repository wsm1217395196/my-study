package com.study.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 分页结果
 */
@Getter
@Setter
@ToString
@ApiModel(value = "PageResult对象", description = "分页结果")
public class PageResult {

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
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private int totalPage;
    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private int total;
    /**
     * 记录
     */
    @ApiModelProperty(value = "记录")
    private List records;

    public PageResult() {
    }

    public PageResult(Integer pageIndex, Integer pageSize, Integer total, List records) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records;
        if (pageIndex >= 0 && pageSize != 0 && total != 0) {
            this.totalPage = total / pageSize;
            if (total % pageSize > 0) {
                this.totalPage++;
            }
        }
    }

}
