package com.chen.brand.http.request.User;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserNewPwd {

    @NotNull
    @Size( min = 6, max = 20)
    @ApiModelProperty( required = true)
    private String pwd;
    @NotNull
    @Size( min = 6, max = 20)
    @ApiModelProperty( required = true)
    private String newPwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
