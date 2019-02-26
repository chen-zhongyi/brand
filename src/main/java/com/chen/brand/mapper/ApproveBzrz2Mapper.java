package com.chen.brand.mapper;

import com.chen.brand.model.ApproveBzrz2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApproveBzrz2Mapper {

    int insert(ApproveBzrz2 bzrz);

    int update(ApproveBzrz2 bzrz);

    int delete(@Param("id") Long id);

    ApproveBzrz2 findOne(@Param("id") Long id);

    List<ApproveBzrz2> findAll(@Param("areaCode") String areaCode,
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

    List<ApproveBzrz2> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Long status);

}
