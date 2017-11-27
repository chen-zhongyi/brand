package com.chen.brand.service.imp;

import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.mapper.ApproveJwsbMapper;
import com.chen.brand.model.ApproveJwsb;
import com.chen.brand.service.ApproveJwsbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApproveJwsbServiceImp implements ApproveJwsbService{

    @Autowired
    private ApproveJwsbMapper jwsbMapper;

    public Long insert(ApproveJwsb jwsb){
        jwsbMapper.insert(jwsb);
        return jwsb.getId();
    }

    public void update(ApproveJwsb jwsb){
        jwsbMapper.update(jwsb);
    }

    public void delete(Long id){
        jwsbMapper.delete(id);
    }

    public ApproveJwsb findOne(Long id){
        return jwsbMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", jwsbMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", jwsbMapper.count(areaCode, sampleName, status, userId));
        return data;
    }

    public boolean isExist(Long id){
        return jwsbMapper.isExist(id) > 0;
    }

    public int[] total(Long userId){
        int[] ans = new int[2];
        ans[0] = jwsbMapper.count(null, null, null, userId);
        ans[1] = jwsbMapper.count(null, null, ApproveStatus.FinalApprovePass.getStatus(), userId);
        return ans;
    }
}
