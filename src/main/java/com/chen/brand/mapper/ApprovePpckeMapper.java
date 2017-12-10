package com.chen.brand.mapper;

import com.chen.brand.model.ApprovePpcke;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApprovePpckeMapper {

    int insert(ApprovePpcke ppcke);

    int update(ApprovePpcke ppcke);

    int delete(@Param("id") Long id);

    ApprovePpcke findOne(@Param("id") Long id);

    List<ApprovePpcke> findAll(@Param("areaCode") String areaCode,
                               @Param("sampleName") String sampleName,
                               @Param("status") Long status,
                               @Param("userId") Long userId,
                               @Param("year") String year,
                               @Param("start") int start,
                               @Param("pageSize") int pageSize);

    int count(@Param("areaCode") String areaCode,
              @Param("sampleName") String sampleName,
              @Param("status") Long status,
              @Param("userId") Long userId,
              @Param("year") String year);

    int isExist(@Param("id") Long id);

    List<ApprovePpcke> findByYear(@Param("userId") Long userId, @Param("year") String year);

    ApprovePpcke findByUserIdAndBrandIdAndYearAndStatus(@Param("userId") Long userId,
                                              @Param("brandId") Long brandId,
                                              @Param("year") String year,
                                              @Param("status") Long status);

}
