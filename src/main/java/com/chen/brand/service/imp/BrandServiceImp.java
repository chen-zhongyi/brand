package com.chen.brand.service.imp;

import com.chen.brand.mapper.BrandMapper;
import com.chen.brand.model.Brand;
import com.chen.brand.model.BrandApprove;
import com.chen.brand.service.BrandApproveService;
import com.chen.brand.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImp implements BrandService{

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private BrandApproveService brandApproveService;

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
        return isBest(brandMapper.findOne(id));
    }

    public Map<String, Object> findAll(String areaCode, Long sampleId, String ppmc, Long status, int pageNumber, int pageSize){
        Map<String, Object> data = new HashMap<>();
        List<Brand> brands = brandMapper.findAll(areaCode, sampleId, ppmc, status, (pageNumber - 1) * pageSize, pageSize);
        for(Brand brand : brands){
            isBest(brand);
        }
        data.put("list", brands);
        data.put("total", brandMapper.count(areaCode, sampleId, ppmc, status));
        return data;
    }

    public void approve(Long id, Long status){
        brandMapper.approve(id, status);
    }

    public boolean isExist(Long id){
        return brandMapper.isExist(id) > 0;
    }

    public List<Brand> findByUserIdAndStatus(Long userId, Long status){
        return brandMapper.findByUserIdAndStatus(userId, status);
    }

    private Brand isBest(Brand brand){
        List<BrandApprove> brandApproves = brandApproveService.findByBrandId(brand.getId());
        if(brandApproves.size() > 0){
            brand.setEverBest(true);
            brand.setYear(brandApproves.get(0).getYear());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int year = calendar.get(Calendar.YEAR);
            brand.setBest(year - Integer.valueOf(brandApproves.get(0).getYear()) < 3);
        }
        return brand;
    }
}
