package com.chen.brand.http.request.ApproveJwpp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class JwppStatus {

    @Min(1)
    @Max(5)
    private Long status;
    @Size(min = 1, max = 200)
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
