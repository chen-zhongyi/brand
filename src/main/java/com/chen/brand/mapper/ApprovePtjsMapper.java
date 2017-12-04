package com.chen.brand.mapper;

import com.chen.brand.model.ApprovePtjs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApprovePtjsMapper {

    int insert(ApprovePtjs ptjs);

    int update(ApprovePtjs ptjs);

    int delete(@Param("id") Long id);

    ApprovePtjs findOne(@Param("id") Long id);

    List<ApprovePtjs> findAll(@Param("areaCode") String areaCode,
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

    List<ApprovePtjs> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Long status);

}
