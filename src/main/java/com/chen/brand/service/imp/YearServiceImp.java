package com.chen.brand.service.imp;

import com.chen.brand.mapper.YearMapper;
import com.chen.brand.model.Year;
import com.chen.brand.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YearServiceImp implements YearService{

    @Autowired
    private YearMapper yearMapper;

    public void insert(Year year){
        yearMapper.insert(year);
    }

    public void deleteAll(){
        yearMapper.deleteAll();
    }
    public boolean isExist(String year){
        return yearMapper.isExist(year) > 0;
    }
}
