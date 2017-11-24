package com.chen.brand.http.request.table;

import javax.validation.constraints.Min;

public class TableInsert {

    private String name;
    private String theNo;
    @Min(1)
    private Long groupId;
    private String guide;
    private String valiGuide;
    @Min(1)
    private Long orderBy;

    public String getName() {
        return name;
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

    public Long getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Long orderBy) {
        this.orderBy = orderBy;
    }
}
