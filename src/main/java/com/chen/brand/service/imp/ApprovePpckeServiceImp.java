package com.chen.brand.service.imp;

import com.chen.brand.mapper.ApprovePpckeMapper;
import com.chen.brand.model.ApprovePpcke;
import com.chen.brand.service.ApprovePpckeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApprovePpckeServiceImp implements ApprovePpckeService{

    @Autowired
    private ApprovePpckeMapper ppckeMapper;

    public Long insert(ApprovePpcke ppcke){
        ppckeMapper.insert(ppcke);
        return ppcke.getId();
    }

    public void update(ApprovePpcke ppcke){
        ppckeMapper.update(ppcke);
    }

    public void delete(Long id){
        ppckeMapper.delete(id);
    }

    public ApprovePpcke findOne(Long id){
        return ppckeMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, String year, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", ppckeMapper.findAll(areaCode, sampleName, status, userId, year, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", ppckeMapper.count(areaCode, sampleName, status, userId, year));
        return data;
    }

    public boolean isExist(Long id){
        return ppckeMapper.isExist(id) > 0;
    }

    public List<ApprovePpcke> findByYear(Long userId, String year){
        return ppckeMapper.findByYear(userId, year);
    }
}
