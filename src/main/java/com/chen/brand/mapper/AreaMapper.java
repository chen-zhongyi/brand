package com.chen.brand.mapper;

import com.chen.brand.model.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AreaMapper {

    int insert(Area area);

    int update(Area area);

    void delete(@Param("code") String code);

    int isExist(@Param("code") String code);

    List<Area> findAll();

    Area findOne(@Param("code") String code);
}
