package com.chen.brand.Enum;

public enum XyType {

    HG("xy1", "企业进出口信用（海关总署）"), GS("xy2", "纳税信用（国税总局）"), ZJ("xy3", "出入境检验检疫企业信用（质检总局）"), GONG_SHANG("xy4", "国家企业信用（工商总局）"), SJ("xy5", "市级以上信用管理示范");

    private String code;
    private String intro;

    private XyType(String code, String intro){
        this.code = code;
        this.intro = intro;
    }

    public static XyType convert(String code){
        for(XyType type : XyType.values()){
            if(type.code().equals(code))
                return type;
        }
        return null;
    }

    public String code(){
        return this.code;
    }

    public String intro(){
        return this.intro;
    }
}
