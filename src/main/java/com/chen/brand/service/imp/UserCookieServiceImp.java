package com.chen.brand.service.imp;

import com.chen.brand.mapper.UserCookieMapper;
import com.chen.brand.model.UserCookie;
import com.chen.brand.service.UserCookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCookieServiceImp implements UserCookieService{

    @Autowired
    private UserCookieMapper cookieMapper;

    public void insert(UserCookie cookie){
        cookieMapper.insert(cookie);
    }

    public void delete(Long userId, String cookie, String loginIp){
        cookieMapper.delete(userId, cookie, loginIp);
    }

    public UserCookie find(Long userId, String cookie, String loginIp){
        return cookieMapper.find(userId, cookie, loginIp);
    }
}
