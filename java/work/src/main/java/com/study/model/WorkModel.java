package com.study.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 职业信息表
 * </p>
 *
 * @author wsm123
 * @since 2019-02-28
 */
@TableName("work")
public class WorkModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 招聘平台id
     */
    @TableField("recruit_platform_id")
    private Long recruitPlatformId;
    /**
     * 职位id
     */
    @TableField("job_id")
    private Long jobId;
    /**
     * 地点
     */
    private String site;
    /**
     * 日期
     */
    private Date date;
    /**
     * 条件
     */
    private String condition;
    /**
     * 职位条数
     */
    @TableField("job_number")
    private Integer jobNumber;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 是否有效
     */
    @TableField("is_Enabled")
    private Integer isEnabled;
    /**
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecruitPlatformId() {
        return recruitPlatformId;
    }

    public void setRecruitPlatformId(Long recruitPlatformId) {
        this.recruitPlatformId = recruitPlatformId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(Integer jobNumber) {
        this.jobNumber = jobNumber;
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
        return "WorkModel{" +
                "id=" + id +
                ", recruitPlatformId=" + recruitPlatformId +
                ", jobId=" + jobId +
                ", site='" + site + '\'' +
                ", date=" + date +
                ", condition='" + condition + '\'' +
                ", jobNumber=" + jobNumber +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", isEnabled=" + isEnabled +
                ", remark='" + remark + '\'' +
                '}';
    }
}
