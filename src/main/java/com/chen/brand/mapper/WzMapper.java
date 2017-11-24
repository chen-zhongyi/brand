package com.chen.brand.mapper;

import com.chen.brand.model.Wz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WzMapper {

    int insert(Wz wz);

    int update(Wz wz);

    int delete(@Param("id") Long id);

    Wz findOne(@Param("id") Long id);

    List<Wz> findAll();

    int isExist(@Param("id") Long id);

    int changeStatus(@Param("id") Long id, @Param("status") Boolean status);
}
