package com.chen.brand.model;

public class Table {

    private Long id;
    private String name;
    private String theNo;
    private Long groupId;
    private Boolean status;
    private String guide;
    private String valiGuide;
    private Long orderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheNo() {
        return theNo;
    }

    public void setTheNo(String theNo) {
        this.theNo = theNo;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getValiGuide() {
        return valiGuide;
    }

    public void setValiGuide(String valiGuide) {
        this.valiGuide = valiGuide;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }
}
