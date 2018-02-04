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

    public void update(BrandApprove brandApprove){
        brandApproveMapper.update(brandApprove);
    }

    public void delete(Long id){
        brandApproveMapper.delete(id);
    }

    public BrandApprove findOne(Long id) {
        return brandApproveMapper.findOne(id);
    }

    public List<BrandApprove> findAll(String sampleName, String brandName, String areaCode, Long sampleId, Long brandId, int pageNumber, int pageSize){
        return brandApproveMapper.findAll(sampleName, brandName, areaCode, sampleId, brandId, (pageNumber - 1) * pageSize, pageSize);
    }

    public List<BrandApprove> findByBrandId(Long brandId) {
        return brandApproveMapper.findByBrandId(brandId);
    }

    public BrandApprove findByBrandIdAndYear(Long brandId, String year){
        return brandApproveMapper.findByBrandIdAndYear(brandId, year);
    }
}
