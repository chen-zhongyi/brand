package com.chen.brand.http.request.ApproveJwzl;

import java.sql.Date;

public class JwzlRequest {

    private String mc;
    private String zz;
    private String sqh;
    private Date rq;
    private Date dqsj;
    private String zs;

    public Date getDqsj() {
        return dqsj;
    }

    public void setDqsj(Date dqsj) {
        this.dqsj = dqsj;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getZz() {
        return zz;
    }

    public void setZz(String zz) {
        this.zz = zz;
    }

    public String getSqh() {
        return sqh;
    }

    public void setSqh(String sqh) {
        this.sqh = sqh;
    }

    public Date getRq() {
        return rq;
    }

    public void setRq(Date rq) {
        this.rq = rq;
    }

    public String getZs() {
        return zs;
    }

    public void setZs(String zs) {
        this.zs = zs;
    }
}
