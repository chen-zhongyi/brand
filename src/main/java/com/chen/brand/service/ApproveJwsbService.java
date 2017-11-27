package com.chen.brand.service;

import com.chen.brand.model.ApproveJwsb;

import java.util.Map;

public interface ApproveJwsbService {

    Long insert(ApproveJwsb jwsb);

    void update(ApproveJwsb jwsb);

    void delete(Long id);

    ApproveJwsb findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);
}
