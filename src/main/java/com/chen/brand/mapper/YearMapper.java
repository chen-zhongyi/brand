package com.chen.brand.mapper;

import com.chen.brand.model.Year;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface YearMapper {

    int insert(Year year);

    void deleteAll();

    int isExist(@Param("year") String year);
}
