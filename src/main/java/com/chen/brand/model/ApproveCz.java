package com.chen.brand.model;

import java.sql.Date;

public class ApproveCz {

    private Long id;
    private String lx;
    private String mc;
    private Date sj;
    private Long status;
    private Long userId;
    private String firstComment;
    private String finalComment;
    private Long sampleId;
    private String sampleName;
    private String areaCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Date getSj() {
        return sj;
    }

    public void setSj(Date sj) {
        this.sj = sj;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstComment() {
        return firstComment;
    }

    public void setFirstComment(String firstComment) {
        this.firstComment = firstComment;
    }

    public String getFinalComment() {
        return finalComment;
    }

    public void setFinalComment(String finalComment) {
        this.finalComment = finalComment;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
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
