package com.chen.brand.http.request.ApproveCz;


import java.sql.Date;

public class ApproveCzRequest {

    private String lx;
    private String mc;
    private Date sj;

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getMc() {
        return mc;
    }

    public Date getSj() {
        return sj;
    }

    public void setSj(Date sj) {
        this.sj = sj;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
}
