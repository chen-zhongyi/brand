package com.chen.brand.service;

import com.chen.brand.model.Api;

import java.util.List;

public interface ApiService {

    void insert(Api api);

    void update(Api api);

    void delete(Long id);

    Api findOne(Long id);

    List<Api> findAll();

    Boolean isExist(Long id);

    Api findByApi(String api);
}
