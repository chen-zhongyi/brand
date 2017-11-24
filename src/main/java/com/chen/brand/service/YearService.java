package com.chen.brand.service;

import com.chen.brand.model.Year;

public interface YearService {

    void insert(Year year);

    void deleteAll();

    boolean isExist(String year);
}
