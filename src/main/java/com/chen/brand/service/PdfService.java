package com.chen.brand.service;

import com.chen.brand.model.Pdf;

import java.util.List;

public interface PdfService {

    void insert(Pdf pdf);

    void update(Pdf pdf);

    void delete(Long id);

    Pdf findOne(Long id);

    List<Pdf> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    boolean isExist(Long id);
}
