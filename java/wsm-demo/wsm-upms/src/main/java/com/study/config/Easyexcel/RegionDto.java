package com.study.config.Easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import java.io.Serializable;

/**
 * <p>
 * 域（如一个公司）
 * </p>
 *
 * @author wsm
 * @since 2019-07-16
 */
@ColumnWidth(25)
public class RegionDto implements Serializable {

    /**
     * 名称
     */
    @ExcelProperty("名称")
    private String name;
    /**
     * 编码
     */
    @ExcelProperty("编码")
    private String code;
    /**
     * 父级名称
     */
    @ExcelProperty(value = "父级域")
    private String parentName;
    /**
     * 是否有效(0无效，1有效)
     */
    @ExcelProperty("是否有效")
    private Integer isEnabled;
    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
