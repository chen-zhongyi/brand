package com.chen.brand.service.imp;

import com.chen.brand.mapper.LogMapper;
import com.chen.brand.model.Log;
import com.chen.brand.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class LogServiceImp implements LogService{

    @Autowired
    private LogMapper logMapper;

    public void insert(Log log){
        logMapper.insert(log);
    }

    public void delete(Long id){
        logMapper.delete(id);
    }

    public void deleteAll(){
        logMapper.deleteAll();
    }

    public Log findOne(Long id){
        return logMapper.findOne(id);
    }

    public List<Log> findAll(String userName, Date startTime, Date endTime, Long type, int pageNumber, int pageSize){
        return logMapper.findAll(userName, startTime, endTime, type, (pageNumber - 1) * pageSize, pageSize);
    }

    public boolean isExist(Long id){
        return logMapper.isExist(id) > 0;
    }
}
