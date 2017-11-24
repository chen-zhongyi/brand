package com.chen.brand.http.request.api;

import javax.validation.constraints.NotNull;

public class ApiRequest {

    @NotNull
    private String api;
    @NotNull
    private String systemCode;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
