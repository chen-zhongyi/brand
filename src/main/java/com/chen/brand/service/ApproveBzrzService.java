package com.chen.brand.service;

import com.chen.brand.model.ApproveBzrz;

import java.util.Map;

public interface ApproveBzrzService {

    Long insert(ApproveBzrz bzrz);

    void update(ApproveBzrz bzrz);

    void delete(Long id);

    ApproveBzrz findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

}
