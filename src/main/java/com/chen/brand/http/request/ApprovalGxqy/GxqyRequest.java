package com.chen.brand.http.request.ApprovalGxqy;


import java.sql.Date;

public class GxqyRequest {

    private String jb;
    private Date sj;

    public Date getSj() {
        return sj;
    }

    public String getJb() {
        return jb;
    }

    public void setJb(String jb) {
        this.jb = jb;
    }

    public void setSj(Date sj) {
        this.sj = sj;
    }
}
