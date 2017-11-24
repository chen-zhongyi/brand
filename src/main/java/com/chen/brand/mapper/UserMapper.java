package com.chen.brand.mapper;

import com.chen.brand.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {

    int insert(User user);

    int update(User user);

    int isExist(@Param("id") Long id);

    int isExistUserName(@Param("userName") String userName);

    int delete(@Param("id") Long id);

    User findOne(@Param("id") Long id);

    List<User> findAll(@Param("areaCode") String areaCode, @Param("userId") Long userId, @Param("realName") String realName, @Param("type") Integer type, @Param("start") int start, @Param("pageSize") int pageSize);

    int count(@Param("areaCode") String areaCode, @Param("userId") Long userId, @Param("realName") String realName, @Param("type") Integer type);

    int checkPwd(@Param("userName") String userName, @Param("pwd") String Pwd);

    User findByUserName(@Param("userName") String userName);

}
