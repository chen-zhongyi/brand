package com.chen.brand.service.imp;

import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.mapper.ApproveJwppMapper;
import com.chen.brand.model.ApproveJwpp;
import com.chen.brand.service.ApproveJwppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApproveJwppServiceImp implements ApproveJwppService{

    @Autowired
    private ApproveJwppMapper jwppMapper;

    public Long insert(ApproveJwpp jwpp){
        jwppMapper.insert(jwpp);
        return jwpp.getId();
    }

    public void update(ApproveJwpp jwpp){
        jwppMapper.update(jwpp);
    }

    public void delete(Long id){
        jwppMapper.delete(id);
    }

    public ApproveJwpp findOne(Long id){
        return jwppMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", jwppMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", jwppMapper.count(areaCode, sampleName, status, userId));
        return data;
    }

    public boolean isExist(Long id){
        return jwppMapper.isExist(id) > 0;
    }

    public int[] total(Long userId){
        int[] ans = new int[2];
        ans[0] = jwppMapper.count(null, null, null, userId);
        ans[1] = jwppMapper.count(null, null, ApproveStatus.FinalApprovePass.getStatus(), userId);
        return ans;
    }
}
