package com.chen.brand.service;

import com.chen.brand.model.UserCookie;

public interface UserCookieService {

    void insert(UserCookie cookie);

    void delete(Long userId, String cookie, String loginIp);

    UserCookie find(Long userId, String cookie, String loginIp);
}
