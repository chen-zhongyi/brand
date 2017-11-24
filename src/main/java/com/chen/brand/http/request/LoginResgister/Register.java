package com.chen.brand.http.request.LoginResgister;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Register {

    @NotNull
    @Size( min = 4, max = 20)
    private String userName;
    @NotNull
    @Size( min = 2, max = 20)
    private String realName;
    @Size( min = 6, max = 20)
    @NotNull
    private String pwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
