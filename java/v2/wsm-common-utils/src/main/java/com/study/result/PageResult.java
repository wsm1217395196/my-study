package com.study.result;

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
public class PageResult {

    /**
     * 当前页
     */
    private Integer pageIndex;
    /**
     * 当前页多少数据
     */
    private Integer pageSize;
    /**
     * 总记录数
     */
    private Integer total;
    /**
     * 记录
     */
    private List records;

    public PageResult() {
    }

    public PageResult(Integer pageIndex, Integer pageSize, Integer total, List records) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records;
    }

}
