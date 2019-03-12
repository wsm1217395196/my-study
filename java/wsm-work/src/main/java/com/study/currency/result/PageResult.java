package com.study.currency.result;

import java.util.List;

/**
 * 分页结果
 */
public class PageResult {

    private Integer pageIndex;

    private Integer pageSize;

    private Integer total;

    private List list;

    public PageResult() {
    }

    public PageResult(Integer pageIndex, Integer pageSize, Integer total, List list) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
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

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
