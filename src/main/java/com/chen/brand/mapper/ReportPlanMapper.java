package com.chen.brand.mapper;

import com.chen.brand.model.ReportPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ReportPlanMapper {

    int insert(ReportPlan plan);

    int update(ReportPlan plan);

    int delete(@Param("id") Long id);

    ReportPlan findOne(@Param("id") Long id);

    List<ReportPlan> findAll(@Param("start") int start, @Param("pageSize") int pageSize);

    int countAll();

    int isExist(@Param("id") Long id);

    int changeStatus(@Param("id") Long id, @Param("status") Boolean status);
}
