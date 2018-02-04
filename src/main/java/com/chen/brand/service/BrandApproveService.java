package com.chen.brand.service;

import com.chen.brand.model.BrandApprove;

import java.util.List;

public interface BrandApproveService {

    void insert(BrandApprove brandApprove);

    void update(BrandApprove brandApprove);

    void delete(Long id);

    BrandApprove findOne(Long id);

    List<BrandApprove> findAll(String sampleName, String brandName, String areaCode, Long sampleId, Long brandId, int start, int pageSize);

    List<BrandApprove> findByBrandId(Long brandId);

    BrandApprove findByBrandIdAndYear(Long brandId, String year);
}
