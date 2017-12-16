package com.chen.brand.service;

import com.chen.brand.model.Pdf;

import java.util.Map;

public interface PdfService {

    void insert(Pdf pdf);

    void update(Pdf pdf);

    void delete(Long id);

    Pdf findOne(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    boolean isExist(Long id);

    Pdf findByUserIdAndBrandId(Long userId, Long brandId);
}
