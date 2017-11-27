package com.chen.brand.service.imp;

import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.mapper.ApproveCfsmMapper;
import com.chen.brand.model.ApproveCfsm;
import com.chen.brand.service.ApproveCfsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApproveCfsmServiceImp implements ApproveCfsmService{

    @Autowired
    private ApproveCfsmMapper cfsmMapper;

    public Long insert(ApproveCfsm cfsm){
        cfsmMapper.insert(cfsm);
        return cfsm.getId();
    }

    public void update(ApproveCfsm cfsm){
        cfsmMapper.update(cfsm);
    }

    public void delete(Long id){
        cfsmMapper.delete(id);
    }

    public ApproveCfsm findOne(Long id){
        return cfsmMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", cfsmMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", cfsmMapper.count(areaCode, sampleName, status, userId));
        return data;
    }

    public boolean isExist(Long id){
        return cfsmMapper.isExist(id) > 0;
    }

    public int[] total(Long userId){
        int[] ans = new int[2];
        ans[0] = cfsmMapper.count(null, null, null, userId);
        ans[1] = cfsmMapper.count(null, null, ApproveStatus.FinalApprovePass.getStatus(), userId);
        return ans;
    }
}
