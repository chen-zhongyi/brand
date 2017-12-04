package com.chen.brand.service.imp;

import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.mapper.ApproveRychMapper;
import com.chen.brand.model.ApproveRych;
import com.chen.brand.service.ApproveRychService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApproveRychServiceImp implements ApproveRychService{

    @Autowired
    private ApproveRychMapper rychMapper;

    public Long insert(ApproveRych rych){
        rychMapper.insert(rych);
        return rych.getId();
    }

    public void update(ApproveRych rych){
        rychMapper.update(rych);
    }

    public void delete(Long id){
        rychMapper.delete(id);
    }

    public ApproveRych findOne(Long id){
        return rychMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", rychMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", rychMapper.count(areaCode, sampleName, status, userId));
        return data;
    }

    public boolean isExist(Long id){
        return rychMapper.isExist(id) > 0;
    }

    public int[] total(Long userId){
        int[] ans = new int[2];
        ans[0] = rychMapper.count(null, null, null, userId);
        ans[1] = rychMapper.count(null, null, ApproveStatus.FinalApprovePass.getStatus(), userId);
        return ans;
    }

    public List<ApproveRych> findByUserIdAndStatus(Long userId, Long status){
        return rychMapper.findByUserIdAndStatus(userId, status);
    }
}
