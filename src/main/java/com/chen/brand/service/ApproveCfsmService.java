package com.chen.brand.service;

import com.chen.brand.model.ApproveCfsm;

import java.util.List;
import java.util.Map;

public interface ApproveCfsmService {

    Long insert(ApproveCfsm cfsm);

    void update(ApproveCfsm cfsm);

    void delete(Long id);

    ApproveCfsm findOne(Long id);

    boolean isExist(Long id);

    Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize);

    int[] total(Long userId);
    List<ApproveCfsm> findByUserIdAndStatus(Long userId, Long status);
}
