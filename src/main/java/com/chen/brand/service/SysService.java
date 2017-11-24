package com.chen.brand.service;

import com.chen.brand.model.Sys;

import java.util.List;

public interface SysService {

    Long insert(Sys sys);

    Sys findOne(Long id);

    List<Sys> findAll();

    boolean isExistCode(String code);

    boolean isExist(Long id);

    void changeStatus(Long id, Boolean status);
}
