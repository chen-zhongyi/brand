package com.chen.brand.service;

import com.chen.brand.model.Total;

import java.util.Map;

public interface TotalService {

    Total insert(Total total);

    void update(Total total);

    Total findOne(Long id);

    Map<String, Object> findAll(Long userId, String areaCode, int pageNumber, int pageSize);

    int countAll(Long userId, String areaCode);

    Boolean isExist(Long id);

    Total findByUserId(Long userId);
}
