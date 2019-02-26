package com.chen.brand.mapper;

import com.chen.brand.model.ApproveCz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApproveCzMapper {

    int insert(ApproveCz bzrz);

    int update(ApproveCz bzrz);

    int delete(@Param("id") Long id);

    ApproveCz findOne(@Param("id") Long id);

    List<ApproveCz> findAll(@Param("areaCode") String areaCode,
                              @Param("sampleName") String sampleName,
                              @Param("status") Long status,
                              @Param("userId") Long userId,
                              @Param("start") int start,
                              @Param("pageSize") int pageSize);

    int count(@Param("areaCode") String areaCode,
              @Param("sampleName") String sampleName,
              @Param("status") Long status,
              @Param("userId") Long userId);

    int isExist(@Param("id") Long id);

    List<ApproveCz> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Long status);

}
