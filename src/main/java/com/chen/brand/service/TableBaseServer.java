package com.chen.brand.service;

import com.chen.brand.model.TableBase;

public interface TableBaseServer {

    TableBase findByUserIdAndYearAndStatus(Long userId, String year, Long status);
}
