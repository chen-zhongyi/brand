package com.chen.brand.mapper;

import com.chen.brand.model.ApproveJwpp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApproveJwppMapper {

    int insert(ApproveJwpp jwpp);

    int update(ApproveJwpp jwpp);

    int delete(@Param("id") Long id);

    ApproveJwpp findOne(@Param("id") Long id);

    List<ApproveJwpp> findAll(@Param("areaCode") String areaCode,
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

}
