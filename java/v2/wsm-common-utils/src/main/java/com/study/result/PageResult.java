package com.study.result;

import java.util.List;

/**
 * 分页结果
 */
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

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getRecords() {
        return records;
    }

    public void setRecords(List records) {
        this.records = records;
    }
}
