package com.chen.brand.mapper;

import com.chen.brand.model.ApproveJwzl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApproveJwzlMapper {

    int insert(ApproveJwzl jwzl);

    int update(ApproveJwzl jwzl);

    int delete(@Param("id") Long id);

    ApproveJwzl findOne(@Param("id") Long id);

    List<ApproveJwzl> findAll(@Param("areaCode") String areaCode,
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
