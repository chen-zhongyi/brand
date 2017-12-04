package com.chen.brand.mapper;

import com.chen.brand.model.ReApprove;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ReApproveMapper {

    int insert(ReApprove reApprove);

    int update(ReApprove reApprove);

    int delete(@Param("id") Long id);

    ReApprove findOne(@Param("id") Long id);

    List<ReApprove> findAll(@Param("sampleName") String sampleName,
                            @Param("areaCode") String areaCode,
                            @Param("status") Long status,
                            @Param("userId") Long userId,
                            @Param("start") int start,
                            @Param("pageSize") int pageSize);

    int count(@Param("sampleName") String sampleName,
              @Param("areaCode") String areaCode,
              @Param("status") Long status,
              @Param("userId") Long userId);
}
