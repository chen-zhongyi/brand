package com.chen.brand.service;

import com.chen.brand.model.Table;

import java.util.List;

public interface TableService {

    Long insert(Table table);

    void update(Table table);

    Table findOne(Long id);

    List<Table> findAll();

    boolean isExist(Long id);

    void changeStatus(Long id, Boolean status);
}
