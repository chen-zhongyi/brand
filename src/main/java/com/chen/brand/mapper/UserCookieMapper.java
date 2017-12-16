package com.chen.brand.mapper;

import com.chen.brand.model.UserCookie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserCookieMapper {

    int insert(UserCookie cookie);

    int delete(@Param("userId") Long userId,
               @Param("cookie") String cookie,
               @Param("loginIp") String loginIp);

    UserCookie find(@Param("userId") Long userId,
                    @Param("cookie") String cookie,
                    @Param("loginIp") String loginIp);
}
