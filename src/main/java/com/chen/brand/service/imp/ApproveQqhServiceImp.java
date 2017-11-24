package com.chen.brand.service.imp;

import com.chen.brand.mapper.ApproveQqhMapper;
import com.chen.brand.model.ApproveQqh;
import com.chen.brand.service.ApproveQqhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApproveQqhServiceImp implements ApproveQqhService{

    @Autowired
    private ApproveQqhMapper qqhMapper;

    public Long insert(ApproveQqh qqh){
        qqhMapper.insert(qqh);
        return qqh.getId();
    }

    public void update(ApproveQqh qqh){
        qqhMapper.update(qqh);
    }

    public void delete(Long id){
        qqhMapper.delete(id);
    }

    public ApproveQqh findOne(Long id){
        return qqhMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", qqhMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", qqhMapper.count(areaCode, sampleName, status, userId));
        return data;
    }

    public boolean isExist(Long id){
        return qqhMapper.isExist(id) > 0;
    }
}
