package com.chen.brand.service;

import com.chen.brand.model.ApproveJwxc;

import java.util.List;
import java.util.Map;

public interface ApproveJwxcService {

    Long insert(ApproveJwxc bzrz);

    void update(ApproveJwxc bzrz);

    void delete(Long id);

    ApproveJwxc findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);

    List<ApproveJwxc> findByUserIdAndStatus(Long userId, Long status);
}
