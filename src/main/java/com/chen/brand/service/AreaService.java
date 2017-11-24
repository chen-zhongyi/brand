package com.chen.brand.service;

import com.chen.brand.model.Area;

import java.util.List;

public interface AreaService {

    void insert(Area area);

    void update(Area area);

    void delete(String code);

    Area findOne(String code);

    List<Area> findAll();

    boolean isExist(String code);
}
