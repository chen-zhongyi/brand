package com.chen.brand.service;

import com.chen.brand.model.ApproveZmcl;

import java.util.Map;

public interface ApproveZmclService {

    Long insert(ApproveZmcl zmcl);

    void update(ApproveZmcl zmcl);

    void delete(Long id);

    ApproveZmcl findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, String year, int pageNumber, int pageSize);

    int[] total(Long userId, String year);

    ApproveZmcl findByUserIdAndYearAndStatus(Long userId, String year, Long status);
}
