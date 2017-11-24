package com.chen.brand.service.imp;

import com.chen.brand.mapper.ReportPlanMapper;
import com.chen.brand.mapper.WzMapper;
import com.chen.brand.model.ReportPlan;
import com.chen.brand.model.Wz;
import com.chen.brand.service.ReportPlanService;
import com.chen.brand.service.WzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WzServiceImp implements WzService{

    @Autowired
    private WzMapper wzMapper;

    public Long insert(Wz wz){
        wzMapper.insert(wz);
        return wz.getId();
    }

    public void update(Wz wz){
        wzMapper.update(wz);
    }

    public void delete(Long id){
        wzMapper.delete(id);
    }

    public Wz findOne(Long id){
        return wzMapper.findOne(id);
    }

    public List<Wz> findAll(){
        return wzMapper.findAll();
    }

    public boolean isExist(Long id){
        return wzMapper.isExist(id) > 0 ;
    }

    public void changeStatus(Long id, Boolean status){
        wzMapper.changeStatus(id, status);
    }

}
