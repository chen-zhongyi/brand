package com.chen.brand.mapper;

import com.chen.brand.model.Zs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ZsMapper {

    int insert(Zs zs);

    int update(Zs zs);

    int delete(@Param("id") Long id);

    Zs findOne(@Param("id") Long id);

    List<Zs> findAll(@Param("sampleId") Long sampleId);

    int isExist(@Param("id") Long id);
}
