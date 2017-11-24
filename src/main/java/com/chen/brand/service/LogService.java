package com.chen.brand.service;

import com.chen.brand.model.Log;

import java.sql.Date;
import java.util.List;

public interface LogService {

    void insert(Log log);

    void delete(Long id);

    void deleteAll();

    Log findOne(Long id);

    List<Log> findAll(String userName, Date startTime, Date endTime, Long type, int pageNumber, int pageSize);

    boolean isExist(Long id);
}
