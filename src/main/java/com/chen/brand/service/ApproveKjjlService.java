package com.chen.brand.service;

import com.chen.brand.model.ApproveKjjl;

import java.util.Map;

public interface ApproveKjjlService {

    Long insert(ApproveKjjl kjjl);

    void update(ApproveKjjl kjjl);

    void delete(Long id);

    ApproveKjjl findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

}
