package com.chen.brand.service;

import com.chen.brand.model.ApproveJwzl;

import java.util.Map;

public interface ApproveJwzlService {

    Long insert(ApproveJwzl jwzl);

    void update(ApproveJwzl jwzl);

    void delete(Long id);

    ApproveJwzl findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);
}
