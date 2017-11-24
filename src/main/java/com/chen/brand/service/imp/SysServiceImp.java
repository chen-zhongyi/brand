package com.chen.brand.service.imp;

import com.chen.brand.mapper.SysMapper;
import com.chen.brand.model.Sys;
import com.chen.brand.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysServiceImp implements SysService{

    @Autowired
    private SysMapper sysMapper;

    public Long insert(Sys sys){
        sysMapper.insert(sys);
        return sys.getId();
    }

    public Sys findOne(Long id){
        return sysMapper.findOne(id);
    }

    public List<Sys> findAll(){
        return sysMapper.findAll();
    }

    public boolean isExistCode(String code){
        return sysMapper.isExistCode(code) > 0;
    }

    public boolean isExist(Long id){
        return sysMapper.isExist(id) > 0;
    }

    public void changeStatus(Long id, Boolean status){
        sysMapper.changeStatus(id, status);
    }
}
