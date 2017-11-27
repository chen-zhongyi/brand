package com.chen.brand.service;

import com.chen.brand.model.ApproveBzqc;

import java.util.Map;

public interface ApproveBzqcService {

    Long insert(ApproveBzqc bzqc);

    void update(ApproveBzqc bzqc);

    void delete(Long id);

    ApproveBzqc findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);
}
