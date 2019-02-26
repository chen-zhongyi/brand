package com.chen.brand.mapper;

import com.chen.brand.model.ApproveBzrz3;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApproveBzrz3Mapper {

    int insert(ApproveBzrz3 bzrz3);

    int update(ApproveBzrz3 bzrz3);

    int delete(@Param("id") Long id);

    ApproveBzrz3 findOne(@Param("id") Long id);

    List<ApproveBzrz3> findAll(@Param("areaCode") String areaCode,
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

    List<ApproveBzrz3> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Long status);

}
