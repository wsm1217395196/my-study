package com.study.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分页参数类
 */
@Getter
@Setter
@ToString
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

    public int getPageStart() {
        if (pageIndex > 0) {
            return (pageIndex - 1) * pageSize;
        }
        return pageIndex;
    }

}
