package com.chen.brand.service;

import com.chen.brand.model.ApprovePpcke;

import java.util.List;
import java.util.Map;

public interface ApprovePpckeService {

    Long insert(ApprovePpcke ppcke);

    void update(ApprovePpcke ppcke);

    void delete(Long id);

    ApprovePpcke findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, String year, int pageNumber, int pageSize);

    List<ApprovePpcke> findByYear(Long userId, String year);

    int[] total(Long userId, String year);

    ApprovePpcke findByUserIdAndBrandIdAndYearAndStatus(Long userId, Long brandId, String year, Long status);
}
