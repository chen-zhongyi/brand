package com.chen.brand.service.imp;

import com.chen.brand.mapper.BrandApproveMapper;
import com.chen.brand.model.BrandApprove;
import com.chen.brand.service.BrandApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandApproveServiceImp implements BrandApproveService{

    @Autowired
    private BrandApproveMapper brandApproveMapper;

    public void insert(BrandApprove brandApprove){
        brandApproveMapper.insert(brandApprove);
    }

    public List<BrandApprove> findAll(){
        return brandApproveMapper.findAll();
    }

    public List<BrandApprove> findByBrandId(Long brandId){
        return brandApproveMapper.findByBrandId(brandId);
    }
}
