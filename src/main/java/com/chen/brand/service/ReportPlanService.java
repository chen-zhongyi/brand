package com.chen.brand.service;

import com.chen.brand.model.ReportPlan;

import java.util.Map;

public interface ReportPlanService {

    Long insert(ReportPlan plan);

    void update(ReportPlan plan);

    void delete(Long id);

    ReportPlan findOne(Long id);

    Map<String, Object> findAll(int pageNumber, int pageSize);

    boolean isExist(Long id);

    void changeStatus(Long id, Boolean status);
}
