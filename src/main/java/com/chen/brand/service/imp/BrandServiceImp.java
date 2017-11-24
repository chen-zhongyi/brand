package com.chen.brand.service.imp;

import com.chen.brand.mapper.BrandMapper;
import com.chen.brand.model.Brand;
import com.chen.brand.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BrandServiceImp implements BrandService{

    @Autowired
    private BrandMapper brandMapper;

    public Long insert(Brand brand){
        brandMapper.insert(brand);
        return brand.getId();
    }

    public void update(Brand brand){
        brandMapper.update(brand);
    }

    public void delete(Long id){
        brandMapper.delete(id);
    }

    public Brand findOne(Long id){
        return brandMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, Long sampleId, String ppmc, Long status, int pageNumber, int pageSize){
        Map<String, Object> data = new HashMap<>();
        data.put("list", brandMapper.findAll(areaCode, sampleId, ppmc, status, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", brandMapper.count(areaCode, sampleId, ppmc, status));
        return data;
    }

    public void approve(Long id, Long status){
        brandMapper.approve(id, status);
    }

    public boolean isExist(Long id){
        return brandMapper.isExist(id) > 0;
    }
}
