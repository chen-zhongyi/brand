package com.chen.brand.service.imp;

import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.mapper.ApproveXyjsMapper;
import com.chen.brand.model.ApproveXyjs;
import com.chen.brand.service.ApproveXyjsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApproveXyjsServiceImp implements ApproveXyjsService{

    @Autowired
    private ApproveXyjsMapper xyjsMapper;

    public Long insert(ApproveXyjs xyjs){
        xyjsMapper.insert(xyjs);
        return xyjs.getId();
    }

    public void update(ApproveXyjs xyjs){
        xyjsMapper.update(xyjs);
    }

    public void delete(Long id){
        xyjsMapper.delete(id);
    }

    public ApproveXyjs findOne(Long id){
        return xyjsMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, String year, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", xyjsMapper.findAll(areaCode, sampleName, status, userId, year, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", xyjsMapper.count(areaCode, sampleName, status, userId, year));
        return data;
    }

    public boolean isExist(Long id){
        return xyjsMapper.isExist(id) > 0;
    }

    public int[] total(Long userId, String year){
        int[] ans = new int[2];
        ans[0] = xyjsMapper.count(null, null, null, userId, year);
        ans[1] = xyjsMapper.count(null, null, ApproveStatus.FinalApprovePass.getStatus(), userId, year);
        return ans;
    }
}
