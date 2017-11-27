package com.chen.brand.service.imp;

import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.mapper.ApproveJwzlMapper;
import com.chen.brand.model.ApproveJwzl;
import com.chen.brand.service.ApproveJwzlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApproveJwzlServiceImp implements ApproveJwzlService {

    @Autowired
    private ApproveJwzlMapper jwzlMapper;

    public Long insert(ApproveJwzl jwzl){
        jwzlMapper.insert(jwzl);
        return jwzl.getId();
    }

    public void update(ApproveJwzl jwzl){
        jwzlMapper.update(jwzl);
    }

    public void delete(Long id){
        jwzlMapper.delete(id);
    }

    public ApproveJwzl findOne(Long id){
        return jwzlMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", jwzlMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", jwzlMapper.count(areaCode, sampleName, status, userId));
        return data;
    }

    public boolean isExist(Long id){
        return jwzlMapper.isExist(id) > 0;
    }

    public int[] total(Long userId){
        int[] ans = new int[2];
        ans[0] = jwzlMapper.count(null, null, null, userId);
        ans[1] = jwzlMapper.count(null, null, ApproveStatus.FinalApprovePass.getStatus(), userId);
        return ans;
    }
}
