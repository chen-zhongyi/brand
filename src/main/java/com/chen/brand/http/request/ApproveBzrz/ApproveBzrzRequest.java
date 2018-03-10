package com.chen.brand.http.request.ApproveBzrz;

import java.sql.Date;

public class ApproveBzrzRequest {

    private String bz;
    private String tx;
    private String zsbh;
    private Date sj;
    private Date dqsj;
    private String zs;

    public Date getDqsj() {
        return dqsj;
    }

    public void setDqsj(Date dqsj) {
        this.dqsj = dqsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getZsbh() {
        return zsbh;
    }

    public void setZsbh(String zsbh) {
        this.zsbh = zsbh;
    }

    public Date getSj() {
        return sj;
    }

    public void setSj(Date sj) {
        this.sj = sj;
    }

    public String getZs() {
        return zs;
    }

    public void setZs(String zs) {
        this.zs = zs;
    }
}
