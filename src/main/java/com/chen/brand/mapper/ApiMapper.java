package com.chen.brand.mapper;

import com.chen.brand.model.Api;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApiMapper {

    int insert(Api api);

    int update(Api api);

    int delete(@Param("id") Long id);

    Api findOne(@Param("id") Long id);

    List<Api> findAll();

    int isExist(@Param("id") Long id);

    Api findByApi(@Param("api") String api);
}
