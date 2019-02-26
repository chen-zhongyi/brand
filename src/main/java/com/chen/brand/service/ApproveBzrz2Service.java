package com.chen.brand.service;

import com.chen.brand.model.ApproveBzrz2;

import java.util.List;
import java.util.Map;

public interface ApproveBzrz2Service {

    Long insert(ApproveBzrz2 bzrz);

    void update(ApproveBzrz2 bzrz);

    void delete(Long id);

    ApproveBzrz2 findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);

    List<ApproveBzrz2> findByUserIdAndStatus(Long userId, Long status);
}
