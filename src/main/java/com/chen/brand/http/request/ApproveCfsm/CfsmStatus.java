package com.chen.brand.http.request.ApproveCfsm;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class CfsmStatus {

    @Min(1)
    @Max(5)
    private Long status;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
