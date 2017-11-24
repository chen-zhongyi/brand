package com.chen.brand.mapper;

import com.chen.brand.model.Sys;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysMapper {

    int insert(Sys sys);

    Sys findOne(@Param("id") Long id);

    List<Sys> findAll();

    int isExistCode(@Param("code") String code);

    int isExist(@Param("id") Long id);

    int changeStatus(@Param("id") Long id, @Param("status") Boolean status);
}
