package com.chen.brand.service.imp;

import com.chen.brand.mapper.ApproveBzqcMapper;
import com.chen.brand.model.ApproveBzqc;
import com.chen.brand.service.ApproveBzqcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApproveBzqcServiceImp implements ApproveBzqcService {

    @Autowired
    private ApproveBzqcMapper bzqcMapper;

    public Long insert(ApproveBzqc bzqc){
        bzqcMapper.insert(bzqc);
        return bzqc.getId();
    }

    public void update(ApproveBzqc bzqc){
        bzqcMapper.update(bzqc);
    }

    public void delete(Long id){
        bzqcMapper.delete(id);
    }

    public ApproveBzqc findOne(Long id){
        return bzqcMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", bzqcMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", bzqcMapper.count(areaCode, sampleName, status, userId));
        return data;
    }

    public boolean isExist(Long id){
        return bzqcMapper.isExist(id) > 0;
    }
}
