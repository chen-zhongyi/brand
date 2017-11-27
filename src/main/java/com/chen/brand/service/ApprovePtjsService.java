package com.chen.brand.service;

import com.chen.brand.model.ApprovePtjs;

import java.util.Map;

public interface ApprovePtjsService {

    Long insert(ApprovePtjs ptjs);

    void update(ApprovePtjs ptjs);

    void delete(Long id);

    ApprovePtjs findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);
}
