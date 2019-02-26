package com.chen.brand.service;

import com.chen.brand.model.ApprovalScrk;

import java.util.List;
import java.util.Map;

public interface ApproveScrkService {

    Long insert(ApprovalScrk bzrz);

    void update(ApprovalScrk bzrz);

    void delete(Long id);

    ApprovalScrk findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);

    List<ApprovalScrk> findByUserIdAndStatus(Long userId, Long status);
}
