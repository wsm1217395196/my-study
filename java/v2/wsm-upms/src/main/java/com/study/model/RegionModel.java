package com.study.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.study.config.Easyexcel.CustomDateConverter;
import com.study.config.Easyexcel.CustomIntegerConverter;

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
@TableName("region")
@ColumnWidth(25)
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
    @ExcelProperty(value = "编码")
    private String code;
    /**
     * 父级id
     */
    @ExcelIgnore
    @TableField("parent_id")
    private Long parentId;
    /**
     * 父级名称
     */
    @ExcelProperty(value = "父级域")
    @TableField(exist = false)
    private String parentName;
    /**
     * 创建人
     */
    @ExcelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间", converter = CustomDateConverter.class)
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新人
     */
    @ExcelProperty(value = "更新人")
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @ExcelProperty(value = "更新时间", converter = CustomDateConverter.class)
    @TableField("update_time")
    private Date updateTime;
    /**
     * 是否有效(0无效，1有效)
     */
    @ExcelProperty(value = "是否有效", converter = CustomIntegerConverter.class)
    @TableField("is_enabled")
    private Integer isEnabled;
    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", isEnabled=" + isEnabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}
