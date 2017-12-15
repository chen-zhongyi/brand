package com.chen.brand.service;

import com.chen.brand.model.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {

    Long insert(Brand brand);

    void update(Brand brand);

    void delete(Long id);

    Brand findOne(Long id);

    Map<String, Object> findAll(String areaCode, Long sampleId, String ppmc, Long status, int pageNumber, int pageSize);

    void approve(Long id, Long status);

    boolean isExist(Long id);

    List<Brand> findByUserIdAndStatus(Long userId, Long status);
}
