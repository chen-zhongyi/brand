package com.chen.brand.http.request.LoginResgister;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Login {

    @NotNull
    @Size( min = 4, max = 20)
    private String userName;
    @NotNull
    @Size( min = 6, max = 20)
    private String pwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
