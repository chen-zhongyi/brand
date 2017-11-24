package com.chen.brand.mapper;

import com.chen.brand.model.ApproveXyjs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApproveXyjsMapper {

    int insert(ApproveXyjs xyjs);

    int update(ApproveXyjs xyjs);

    int delete(@Param("id") Long id);

    ApproveXyjs findOne(@Param("id") Long id);

    List<ApproveXyjs> findAll(@Param("areaCode") String areaCode,
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

}
