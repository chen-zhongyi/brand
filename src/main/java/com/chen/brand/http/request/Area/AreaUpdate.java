package com.chen.brand.http.request.Area;

import javax.validation.constraints.*;

public class AreaUpdate {

    private String pcode;
    @Size(min = 1, max = 20)
    private String name;
    @Min(0)
    @Max(1)
    private Integer level;
    @Min(0)
    @Max(99)
    private Integer orderNo;

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
