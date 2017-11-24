package com.chen.brand.http.request.ApproveJwpp;

import java.sql.Date;

public class JwppRequest {

    private String mc;
    private String tp;
    private String szg;
    private Date sj;
    private String cl;

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getTp() {
        return tp;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public String getSzg() {
        return szg;
    }

    public void setSzg(String szg) {
        this.szg = szg;
    }

    public Date getSj() {
        return sj;
    }

    public void setSj(Date sj) {
        this.sj = sj;
    }

    public String getCl() {
        return cl;
    }

    public void setCl(String cl) {
        this.cl = cl;
    }
}
