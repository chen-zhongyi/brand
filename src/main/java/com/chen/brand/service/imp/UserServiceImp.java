package com.chen.brand.service.imp;

import com.chen.brand.mapper.UserMapper;
import com.chen.brand.model.User;
import com.chen.brand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserMapper userMapper;

    public Long insert(User user){
        userMapper.insert(user);
        return user.getId();
    }

    public void update(User user){
        userMapper.update(user);
    }

    public void delete(Long id){
        userMapper.delete(id);
    }

    public User findOne(Long id){
        return userMapper.findOne(id);
    }

    public List<User> findAll(String areaCode, Long userId, String realName, Integer type, int pageNumber, int pageSize){
        return userMapper.findAll(areaCode, userId, realName, type, (pageNumber - 1) * pageSize, pageSize);
    }

    public int count(String areaCode, Long userId, String realName, Integer type){
        return userMapper.count(areaCode, userId, realName, type);
    }

    public boolean isExist(Long id){
        return userMapper.isExist(id) > 0;
    }

    public boolean isExistUserName(String userName){
        return userMapper.isExistUserName(userName) > 0;
    }

    public boolean checkPwd(String userName, String pwd){
        return userMapper.checkPwd(userName, pwd) > 0;
    }

    public User findByUserName(String userName){
        return userMapper.findByUserName(userName);
    }
}
