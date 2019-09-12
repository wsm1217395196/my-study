package com.study.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 域（如一个公司）
 * </p>
 *
 * @author wsm
 * @since 2019-07-16
 */
@ColumnWidth(25)
@TableName("region")
public class RegionModel implements Serializable {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    private Long id;
    /**
     * 名称
     */
    @ExcelProperty(value = "名称")
    private String name;
    /**
     * 编码
     */
    @ExcelProperty("编码")
    private String code;
    /**
     * 父级id
     */
    @ExcelProperty("父级域")
    @TableField("parent_id")
    private Long parentId;
    /**
     * 创建人
     */
    @ExcelIgnore
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @ExcelIgnore
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新人
     */
    @ExcelIgnore
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @ExcelIgnore
    @TableField("update_time")
    private Date updateTime;
    /**
     * 是否有效(0无效，1有效)
     */
    @ExcelProperty("是否有效")
    @TableField("is_enabled")
    private Integer isEnabled;
    /**
     * 备注
     */
    @ExcelProperty("备注")
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    @Override
    public String toString() {
        return "RegionModel{" +
                ", id=" + id +
                ", name=" + name +
                ", code=" + code +
                ", parentId=" + parentId +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                ", isEnabled=" + isEnabled +
                ", remark=" + remark +
                "}";
    }
}
