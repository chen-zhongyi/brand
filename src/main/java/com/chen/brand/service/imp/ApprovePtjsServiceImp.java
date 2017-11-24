package com.chen.brand.service.imp;

import com.chen.brand.mapper.ApprovePtjsMapper;
import com.chen.brand.model.ApprovePtjs;
import com.chen.brand.service.ApprovePtjsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApprovePtjsServiceImp implements ApprovePtjsService {

    @Autowired
    private ApprovePtjsMapper ptjsMapper;

    public Long insert(ApprovePtjs ptjs){
        ptjsMapper.insert(ptjs);
        return ptjs.getId();
    }

    public void update(ApprovePtjs ptjs){
        ptjsMapper.update(ptjs);
    }

    public void delete(Long id){
        ptjsMapper.delete(id);
    }

    public ApprovePtjs findOne(Long id){
        return ptjsMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", ptjsMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", ptjsMapper.count(areaCode, sampleName, status, userId));
        return data;
    }

    public boolean isExist(Long id){
        return ptjsMapper.isExist(id) > 0;
    }
}
