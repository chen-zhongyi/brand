package com.chen.brand.model;

import java.sql.Date;

public class TableBase {

    private Long id;
    private Double zczb;
    private Double zzc;
    private Double jzc;
    private Double lrze;
    private Double ywsr;
    private Double xse;
    private Double cke;
    private Long hypm;
    private Long ppsl;
    private Long cyry;
    private Long recordId;
    private Integer round;
    private Long ckppsl;
    private Double sjse;
    private Long status;

    private Long sampleId;
    private String sampleName;
    private String areaCode;

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

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Double getSjse() {
        return sjse;
    }

    public void setSjse(Double sjse) {
        this.sjse = sjse;
    }

    public Long getCkppsl() {
        return ckppsl;
    }

    public void setCkppsl(Long ckppsl) {
        this.ckppsl = ckppsl;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getZczb() {
        return zczb;
    }

    public void setZczb(Double zczb) {
        this.zczb = zczb;
    }

    public Double getZzc() {
        return zzc;
    }

    public void setZzc(Double zzc) {
        this.zzc = zzc;
    }

    public Double getJzc() {
        return jzc;
    }

    public void setJzc(Double jzc) {
        this.jzc = jzc;
    }

    public Double getLrze() {
        return lrze;
    }

    public void setLrze(Double lrze) {
        this.lrze = lrze;
    }

    public Double getYwsr() {
        return ywsr;
    }

    public void setYwsr(Double ywsr) {
        this.ywsr = ywsr;
    }

    public Double getXse() {
        return xse;
    }

    public void setXse(Double xse) {
        this.xse = xse;
    }

    public Double getCke() {
        return cke;
    }

    public void setCke(Double cke) {
        this.cke = cke;
    }

    public Long getHypm() {
        return hypm;
    }

    public void setHypm(Long hypm) {
        this.hypm = hypm;
    }

    public Long getPpsl() {
        return ppsl;
    }

    public void setPpsl(Long ppsl) {
        this.ppsl = ppsl;
    }

    public Long getCyry() {
        return cyry;
    }

    public void setCyry(Long cyry) {
        this.cyry = cyry;
    }
}
