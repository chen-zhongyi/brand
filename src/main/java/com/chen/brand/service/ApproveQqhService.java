package com.chen.brand.service;

import com.chen.brand.model.ApproveQqh;

import java.util.Map;

public interface ApproveQqhService {

    Long insert(ApproveQqh qqh);

    void update(ApproveQqh qqh);

    void delete(Long id);

    ApproveQqh findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);
}
