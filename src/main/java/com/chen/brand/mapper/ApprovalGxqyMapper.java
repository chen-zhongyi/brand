package com.chen.brand.mapper;

import com.chen.brand.model.ApprovalGxqy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ApprovalGxqyMapper {


    int insert(ApprovalGxqy gxqy);

    int update(ApprovalGxqy gxqy);

    int delete(@Param("id") Long id);

    ApprovalGxqy findOne(@Param("id") Long id);

    List<ApprovalGxqy> findAll(@Param("areaCode") String areaCode,
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

    List<ApprovalGxqy> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Long status);
}
