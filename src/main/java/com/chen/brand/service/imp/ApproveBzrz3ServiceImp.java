package com.chen.brand.service.imp;

import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.mapper.ApproveBzrz3Mapper;
import com.chen.brand.model.ApproveBzrz3;
import com.chen.brand.service.ApproveBzrz3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApproveBzrz3ServiceImp implements ApproveBzrz3Service {

    @Autowired
    private ApproveBzrz3Mapper bzrzMapper;

    public Long insert(ApproveBzrz3 bzrz){
        bzrzMapper.insert(bzrz);
        return bzrz.getId();
    }

    public void update(ApproveBzrz3 bzrz){
        bzrzMapper.update(bzrz);
    }

    public void delete(Long id){
        bzrzMapper.delete(id);
    }

    public ApproveBzrz3 findOne(Long id){
        return bzrzMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", bzrzMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", bzrzMapper.count(areaCode, sampleName, status, userId));
        return data;
    }

    public boolean isExist(Long id){
        return bzrzMapper.isExist(id) > 0;
    }

    public int[] total(Long userId){
        int[] ans = new int[2];
        ans[0] = bzrzMapper.count(null, null, null, userId);
        ans[1] = bzrzMapper.count(null, null, ApproveStatus.FinalApprovePass.getStatus(), userId);
        return ans;
    }

    public List<ApproveBzrz3> findByUserIdAndStatus(Long userId, Long status){
        return bzrzMapper.findByUserIdAndStatus(userId, status);
    }
}
