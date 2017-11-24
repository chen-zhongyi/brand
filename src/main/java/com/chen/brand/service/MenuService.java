package com.chen.brand.service;

import com.chen.brand.model.Menu;

import java.util.List;

public interface MenuService {

    Long insert(Menu menu);

    void update(Menu menu);

    List<Menu> findAll(String type);

    boolean isExist(Long id);

    Menu findOne(Long id);

    void delete(Long id);

    void changeStatus(Long id, Boolean status);
}
