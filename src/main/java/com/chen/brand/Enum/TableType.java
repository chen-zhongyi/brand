package com.chen.brand.Enum;

public enum TableType {
    BASE(1L, "表1 出口名牌企业基本情况（年报）"), JYGM(2L, "表2 企业跨境电商交易规模（月度）"), XSE(3L, "表3 品牌出口销售额（月报）"), QKDC(4L, "表4 跨境电子商务景气情况调查（月报）");

    private long code;
    private String intro;

    private TableType(long code, String intro){
        this.code = code;
        this.intro = intro;
    }

    public static TableType convert(long code){
        for(TableType type : TableType.values()){
            if(type.code() == code) return type;
        }
        return null;
    }

    public long code(){
        return this.code;
    }

    public String intro(){
        return this.intro;
    }
}
