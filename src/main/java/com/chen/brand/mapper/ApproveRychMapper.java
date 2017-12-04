package com.chen.brand.mapper;

import com.chen.brand.model.ApproveRych;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ApproveRychMapper {

    int insert(ApproveRych rych);

    int update(ApproveRych rych);

    int delete(@Param("id") Long id);

    ApproveRych findOne(@Param("id") Long id);

    List<ApproveRych> findAll(@Param("areaCode") String areaCode,
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

    List<ApproveRych> findByUserIdAndStatus(@Param("userId")Long userId, @Param("status") Long status);

}
