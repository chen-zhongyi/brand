package com.chen.brand.model;

import java.sql.Date;

public class Brand {

    private Long id;
    private String ppmc;
    private String ppsb;
    private Long status;
    private String splx;
    private String pplx;
    private String sp;
    private Date zcrq;
    private String zcd;
    private Long sampleId;
    private String zcdGw;
    private String zs;
    private Long userId;
    private String sampleName;
    private String areaCode;

    private Boolean isBest = false;
    private Boolean isEverBest = false;
    private String year;

    public Boolean getBest() {
        return isBest;
    }

    public void setBest(Boolean best) {
        isBest = best;
    }

    public Boolean getEverBest() {
        return isEverBest;
    }

    public void setEverBest(Boolean everBest) {
        isEverBest = everBest;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getZs() {
        return zs;
    }

    public void setZs(String zs) {
        this.zs = zs;
    }

    public String getZcdGw() {
        return zcdGw;
    }

    public void setZcdGw(String zcdGw) {
        this.zcdGw = zcdGw;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPpmc() {
        return ppmc;
    }

    public void setPpmc(String ppmc) {
        this.ppmc = ppmc;
    }

    public String getPpsb() {
        return ppsb;
    }

    public void setPpsb(String ppsb) {
        this.ppsb = ppsb;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getSplx() {
        return splx;
    }

    public void setSplx(String splx) {
        this.splx = splx;
    }

    public String getPplx() {
        return pplx;
    }

    public void setPplx(String pplx) {
        this.pplx = pplx;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public Date getZcrq() {
        return zcrq;
    }

    public void setZcrq(Date zcrq) {
        this.zcrq = zcrq;
    }

    public String getZcd() {
        return zcd;
    }

    public void setZcd(String zcd) {
        this.zcd = zcd;
    }
}
