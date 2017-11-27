package com.chen.brand.service;

import com.chen.brand.model.ApproveJwpp;

import java.util.Map;

public interface ApproveJwppService {

    Long insert(ApproveJwpp jwpp);

    void update(ApproveJwpp jwpp);

    void delete(Long id);

    ApproveJwpp findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);
}
