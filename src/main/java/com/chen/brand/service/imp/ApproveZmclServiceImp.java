package com.chen.brand.service.imp;

import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.mapper.ApproveZmclMapper;
import com.chen.brand.model.ApproveZmcl;
import com.chen.brand.service.ApproveZmclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApproveZmclServiceImp implements ApproveZmclService{

    @Autowired
    private ApproveZmclMapper zmclMapper;

    public Long insert(ApproveZmcl zmcl){
        zmclMapper.insert(zmcl);
        return zmcl.getId();
    }

    public void update(ApproveZmcl zmcl){
        zmclMapper.update(zmcl);
    }

    public void delete(Long id){
        zmclMapper.delete(id);
    }

    public ApproveZmcl findOne(Long id){
        return zmclMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, String year, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", zmclMapper.findAll(areaCode, sampleName, status, userId, year, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", zmclMapper.count(areaCode, sampleName, status, userId, year));
        return data;
    }

    public boolean isExist(Long id){
        return zmclMapper.isExist(id) > 0;
    }

    public int[] total(Long userId, String year){
        int[] ans = new int[2];
        ans[0] = zmclMapper.count(null, null, null, userId, year);
        ans[1] = zmclMapper.count(null, null, ApproveStatus.FinalApprovePass.getStatus(), userId, year);
        return ans;
    }
}
