package com.chen.brand.http.request.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
public class UserInsert {
    @NotNull
    @Size( min = 4, max = 20)
    @ApiModelProperty(required = true, reference = "Size(min = 4, max = 20)")
    private String userName;
    @NotNull
    @Size( min = 6, max = 20)
    @ApiModelProperty( required = true)
    private String pwd;
    @NotNull
    @Size( min = 2, max = 20)
    @ApiModelProperty( required = true)
    private String realName;
    @NotNull
    private String areaCode;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
