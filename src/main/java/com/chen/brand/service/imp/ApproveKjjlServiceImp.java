package com.chen.brand.service.imp;

import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.mapper.ApproveKjjlMapper;
import com.chen.brand.model.ApproveKjjl;
import com.chen.brand.service.ApproveKjjlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApproveKjjlServiceImp implements ApproveKjjlService {

    @Autowired
    private ApproveKjjlMapper kjjlMapper;

    public Long insert(ApproveKjjl kjjl){
        kjjlMapper.insert(kjjl);
        return kjjl.getId();
    }

    public void update(ApproveKjjl kjjl){
        kjjlMapper.update(kjjl);
    }

    public void delete(Long id){
        kjjlMapper.delete(id);
    }

    public ApproveKjjl findOne(Long id){
        return kjjlMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", kjjlMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", kjjlMapper.count(areaCode, sampleName, status, userId));
        return data;
    }

    public boolean isExist(Long id){
        return kjjlMapper.isExist(id) > 0;
    }

    public int[] total(Long userId){
        int[] ans = new int[2];
        ans[0] = kjjlMapper.count(null, null, null, userId);
        ans[1] = kjjlMapper.count(null, null, ApproveStatus.FinalApprovePass.getStatus(), userId);
        return ans;
    }

    public List<ApproveKjjl> findByUserIdAndStatus(Long userId, Long status){
        return kjjlMapper.findByUserIdAndStatus(userId, status);
    }
}
