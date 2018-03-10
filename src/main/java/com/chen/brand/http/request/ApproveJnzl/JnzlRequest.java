package com.chen.brand.http.request.ApproveJnzl;

import java.sql.Date;

public class JnzlRequest {

    private String mc;
    private String lx;
    private String zlh;
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

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getZlh() {
        return zlh;
    }

    public void setZlh(String zlh) {
        this.zlh = zlh;
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
