package com.chen.brand.service;

import com.chen.brand.model.BrandApprove;

import java.util.List;

public interface BrandApproveService {

    void insert(BrandApprove brandApprove);

    List<BrandApprove> findAll();

    List<BrandApprove> findByBrandId(Long brandId);
}
