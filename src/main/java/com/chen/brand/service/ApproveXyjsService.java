package com.chen.brand.service;

import com.chen.brand.model.ApproveXyjs;

import java.util.Map;

public interface ApproveXyjsService {

    Long insert(ApproveXyjs xyjs);

    void update(ApproveXyjs xyjs);

    void delete(Long id);

    ApproveXyjs findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, String year, int pageNumber, int pageSize);

    int[] total(Long userId, String year);
}
