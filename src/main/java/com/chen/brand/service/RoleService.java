package com.chen.brand.service;

import com.chen.brand.model.Role;

import java.util.List;

public interface RoleService {

    Long insert(Role role);

    void update(Role role);

    Role findOne(Long id);

    List<Role> findAll();

    boolean isExist(Long id);

    boolean isExistCode(String code);

    void changeStatus(Long id, Boolean status);
}
