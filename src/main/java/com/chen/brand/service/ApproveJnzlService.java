package com.chen.brand.service;

import com.chen.brand.model.ApproveJnzl;

import java.util.Map;

public interface ApproveJnzlService {

    Long insert(ApproveJnzl jnzl);

    void update(ApproveJnzl jnzl);

    void delete(Long id);

    ApproveJnzl findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

}
