package com.chen.brand.service;

import com.chen.brand.model.ApprovalGxqy;

import java.util.List;
import java.util.Map;

public interface ApprovalGxqyService {

    Long insert(ApprovalGxqy jnzl);

    void update(ApprovalGxqy jnzl);

    void delete(Long id);

    ApprovalGxqy findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);

    List<ApprovalGxqy> findByUserIdAndStatus(Long userId, Long status);
}
