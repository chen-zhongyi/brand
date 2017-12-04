package com.chen.brand.service;

import com.chen.brand.model.ApproveRych;

import java.util.List;
import java.util.Map;

public interface ApproveRychService {

    Long insert(ApproveRych rych);

    void update(ApproveRych rych);

    void delete(Long id);

    ApproveRych findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);

    List<ApproveRych> findByUserIdAndStatus(Long userId, Long status);
}
