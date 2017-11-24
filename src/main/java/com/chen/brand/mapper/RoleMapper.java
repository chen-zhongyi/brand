package com.chen.brand.mapper;

import com.chen.brand.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RoleMapper {

    int insert(Role role);

    int update(Role role);

    Role findOne(@Param("id") Long id);

    List<Role> findAll();

    int isExistCode(@Param("code") String code);

    int isExist(@Param("id") Long id);

    int changeStatus(@Param("id") Long id, @Param("status") Boolean status);
}
