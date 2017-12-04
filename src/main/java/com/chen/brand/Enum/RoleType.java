package com.chen.brand.Enum;

public enum RoleType {

    SYSTEM("system", "系统管理员"), ADMIN("admin", "市管理员"), QX_ADMIN("qx_admin", "区县管理员"), USER("user", "用户");

    private String code;
    private String intro;

    private RoleType(String code, String intro){
        this.code = code;
        this.intro = intro;
    }

    public String code(){
        return this.code;
    }

    public String intro(){
        return this.intro;
    }
}
