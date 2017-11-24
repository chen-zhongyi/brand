package com.chen.brand.service;

import com.chen.brand.model.Wz;

import java.util.List;

public interface WzService {

    Long insert(Wz wz);

    void update(Wz wz);

    void delete(Long id);

    Wz findOne(Long id);

    List<Wz> findAll();

    boolean isExist(Long id);

    void changeStatus(Long id, Boolean status);
}
