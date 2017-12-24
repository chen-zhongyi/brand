package com.chen.brand.http.request.User;

import javax.validation.constraints.Size;

public class UserUpdate {

    @Size( min = 2, max = 20)
    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
