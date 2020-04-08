package com.study.result;

/**
 * 分页参数类
 */
public class PageParam {
    /**
     * 当前页
     */
    private int pageIndex;
    /**
     * 当前页有多少数据
     */
    private int pageSize;
    /**
     * 要查的字段(别名=model属性名)
     */
    private String sqlColumns;
    /**
     * 排序
     */
    private String sort;
    /**
     * 条件
     */
    private String condition;

    public PageParam() {
    }

    public PageParam(int pageIndex, int pageSize, String sort, String condition) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.sort = sort;
        this.condition = condition;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSqlColumns() {
        return sqlColumns;
    }

    public void setSqlColumns(String sqlColumns) {
        this.sqlColumns = sqlColumns;
    }

    public int getPageStart() {
        if (pageIndex > 0) {
            return (pageIndex - 1) * pageSize;
        }
        return pageIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
