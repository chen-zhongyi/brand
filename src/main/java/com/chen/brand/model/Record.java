package com.chen.brand.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Record {

    private Long id;
    private Long planId;
    private Long tableId;
    private Long groupId;
    private Long sampleId;
    private Long status;
    private Timestamp createAt;
    private Long createBy;
    private Timestamp firstApproveAt;
    private Long firstApproveBy;
    private Timestamp finalApproveAt;
    private Long finalApproveBy;
    private String firstApproveComment;
    private String finalApproveComment;
    private String tableReportId;
    private Timestamp modifyAt;
    private Long modifyBy;
    private String tableName;
    private Date planRound;
    private String sampleName;
    private String areaCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Timestamp getFirstApproveAt() {
        return firstApproveAt;
    }

    public void setFirstApproveAt(Timestamp firstApproveAt) {
        this.firstApproveAt = firstApproveAt;
    }

    public Long getFirstApproveBy() {
        return firstApproveBy;
    }

    public void setFirstApproveBy(Long firstApproveBy) {
        this.firstApproveBy = firstApproveBy;
    }

    public Timestamp getFinalApproveAt() {
        return finalApproveAt;
    }

    public void setFinalApproveAt(Timestamp finalApproveAt) {
        this.finalApproveAt = finalApproveAt;
    }

    public Long getFinalApproveBy() {
        return finalApproveBy;
    }

    public void setFinalApproveBy(Long finalApproveBy) {
        this.finalApproveBy = finalApproveBy;
    }

    public String getFirstApproveComment() {
        return firstApproveComment;
    }

    public void setFirstApproveComment(String firstApproveComment) {
        this.firstApproveComment = firstApproveComment;
    }

    public String getFinalApproveComment() {
        return finalApproveComment;
    }

    public void setFinalApproveComment(String finalApproveComment) {
        this.finalApproveComment = finalApproveComment;
    }

    public String getTableReportId() {
        return tableReportId;
    }

    public void setTableReportId(String tableReportId) {
        this.tableReportId = tableReportId;
    }

    public Timestamp getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Timestamp modifyAt) {
        this.modifyAt = modifyAt;
    }

    public Long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Long modifyBy) {
        this.modifyBy = modifyBy;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Date getPlanRound() {
        return planRound;
    }

    public void setPlanRound(Date planRound) {
        this.planRound = planRound;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
