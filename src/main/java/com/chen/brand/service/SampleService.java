package com.chen.brand.service;

import com.chen.brand.model.Sample;
import com.chen.brand.model.Zs;

import java.util.List;
import java.util.Map;

public interface SampleService {

    Long insert(Sample sample, List<Zs> bgzs);

    void update(Sample sample, List<Zs> bgzs);

    void delete(Long id);

    Map<String, Object> findOne(Long id);

    Map<String, Object> findAll(String areaCode, Long userId, String xydm, String qymc, int pageNumber, int pageSize);

    boolean isExist(Long id );

    void approve(Long id, Long status);

    Sample findByUserId(Long userId);
}
