package com.chen.brand.http.request.Area;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

public class AreaInsert {

    @NotNull
    @Size( min = 4, max = 4)
    @ApiModelProperty( required = true)
    private String code;
    @NotNull
    @Size(min = 1, max = 20)
    @ApiModelProperty( required = true)
    private String name;
    private String pcode;
    @NotNull
    @Min(0)
    @Max(1)
    @ApiModelProperty( required = true)
    private Integer level;
    @NotNull
    @Min(0)
    @Max(99)
    @ApiModelProperty( required = true)
    private Integer orderNo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
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
