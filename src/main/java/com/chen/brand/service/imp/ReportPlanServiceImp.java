package com.chen.brand.service.imp;

import com.chen.brand.mapper.ReportPlanMapper;
import com.chen.brand.model.ReportPlan;
import com.chen.brand.service.ReportPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportPlanServiceImp implements ReportPlanService{

    @Autowired
    private ReportPlanMapper planMapper;

    public Long insert(ReportPlan plan){
        planMapper.insert(plan);
        return plan.getId();
    }

    public void update(ReportPlan plan){
        planMapper.update(plan);
    }

    public void delete(Long id){
        planMapper.delete(id);
    }

    public ReportPlan findOne(Long id){
        return planMapper.findOne(id);
    }

    public Map<String, Object> findAll(int pageNumber, int pageSize){
        Map<String, Object> data = new HashMap<>();
        data.put("total", planMapper.countAll());
        data.put("list", planMapper.findAll((pageNumber - 1) * pageSize, pageSize));
        return data;
    }

    public boolean isExist(Long id){
        return planMapper.isExist(id) > 0 ;
    }

    public void changeStatus(Long id, Boolean status){
        planMapper.changeStatus(id, status);
    }

}
