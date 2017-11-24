package com.chen.brand.Enum;

public enum UserType {

    SYSTEM_ADMIN(0L, "系统管理员"), ADMIN(1L, "市级管理员"), QX_ADMIN(2L, "区县管理员"), USER(3L, "用户");

    private Long code;
    private String description;

    private UserType(Long code, String description){
        this.code = code;
        this.description = description;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
