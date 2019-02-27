package com.chen.brand.mapper;

import com.chen.brand.model.ApproveJwxc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApproveJwxcMapper {

    int insert(ApproveJwxc bzrz);

    int update(ApproveJwxc bzrz);

    int delete(@Param("id") Long id);

    ApproveJwxc findOne(@Param("id") Long id);

    List<ApproveJwxc> findAll(@Param("areaCode") String areaCode,
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

    List<ApproveJwxc> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Long status);

}
