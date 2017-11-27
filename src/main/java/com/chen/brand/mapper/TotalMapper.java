package com.chen.brand.mapper;

import com.chen.brand.model.Total;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TotalMapper {

    int insert(Total total);

    int update(Total total);

    Total findOne(@Param("id") Long id);

    List<Total> findAll(@Param("userId") Long userId, @Param("areaCode") String areaCode, @Param("start") int start, @Param("pageSize") int pageSize);

    int countAll(@Param("userId") Long userId, @Param("areaCode") String areaCode);

    int isExist(@Param("id") Long id);

    Total findByUserId(@Param("userId") Long id);

}
