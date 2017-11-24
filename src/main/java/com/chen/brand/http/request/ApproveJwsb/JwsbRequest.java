package com.chen.brand.http.request.ApproveJwsb;

import java.sql.Date;

public class JwsbRequest {

    private String mc;
    private String tp;
    private String xs;
    private Date sj;
    private String cl;
    private String dygj;

    public String getDygj() {
        return dygj;
    }

    public void setDygj(String dygj) {
        this.dygj = dygj;
    }

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

    public String getXs() {
        return xs;
    }

    public void setXs(String xs) {
        this.xs = xs;
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
