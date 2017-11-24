package com.chen.brand.service;

import com.chen.brand.model.User;

import java.util.List;

public interface UserService {

    Long insert(User user);

    void update(User user);

    void delete(Long id);

    boolean isExist(Long id);

    boolean isExistUserName(String userName);

    User findOne(Long id);

    List<User> findAll(String areaCode, Long userId, String realName, Integer type, int pageNumber, int pageSize);

    int count(String areaCode, Long userId, String realName, Integer type);

    boolean checkPwd(String userName, String pwd);

    User findByUserName(String userName);
}
