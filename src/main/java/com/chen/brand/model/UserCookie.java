package com.chen.brand.model;

public class UserCookie {

    private Long id;
    private Long userId;
    private String cookie;
    private String loginIp;

    public UserCookie(){}

    public UserCookie(Long userId, String cookie, String loginIp){
        this.userId = userId;
        this.cookie = cookie;
        this.loginIp = loginIp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
