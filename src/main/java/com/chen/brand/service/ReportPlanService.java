package com.chen.brand.service;

import com.chen.brand.model.ReportPlan;

import java.util.List;

public interface ReportPlanService {

    Long insert(ReportPlan plan);

    void update(ReportPlan plan);

    void delete(Long id);

    ReportPlan findOne(Long id);

    List<ReportPlan> findAll();

    boolean isExist(Long id);

    void changeStatus(Long id, Boolean status);
}
