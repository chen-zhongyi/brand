package com.chen.brand.service;

import com.chen.brand.model.ApproveCz;

import java.util.List;
import java.util.Map;

public interface ApproveCzService {

    Long insert(ApproveCz bzrz);

    void update(ApproveCz bzrz);

    void delete(Long id);

    ApproveCz findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);

    List<ApproveCz> findByUserIdAndStatus(Long userId, Long status);
}
