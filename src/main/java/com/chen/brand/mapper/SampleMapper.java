package com.chen.brand.mapper;

import com.chen.brand.model.Sample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
@Component
public interface SampleMapper{

    int insert(Sample sample);

    int update(Sample sample);

    int delete(@Param("id") Long id);

    Sample findOne(@Param("id") Long id);

    Sample findByUserId(@Param("userId") Long userId);

    List<Sample> findAll(@Param("areaCode") String areaCode, @Param("userId") Long userId, @Param("xydm") String xydm, @Param("qymc") String qymc, @Param("start") int start, @Param("pageSize") int pageSize);

    int count(@Param("areaCode") String areaCode, @Param("userId") Long userId, @Param("xydm") String xydm, @Param("qymc") String qymc);

    int isExist(@Param("id") Long id);

    int approve(@Param("id") Long id, @Param("status") Long status);
}
