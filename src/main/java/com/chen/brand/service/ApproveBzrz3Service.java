package com.chen.brand.service;

import com.chen.brand.model.ApproveBzrz3;

import java.util.List;
import java.util.Map;

public interface ApproveBzrz3Service {

    Long insert(ApproveBzrz3 bzrz);

    void update(ApproveBzrz3 bzrz);

    void delete(Long id);

    ApproveBzrz3 findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);

    List<ApproveBzrz3> findByUserIdAndStatus(Long userId, Long status);
}
