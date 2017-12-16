package com.chen.brand.service.imp;

import com.chen.brand.mapper.PdfMapper;
import com.chen.brand.model.Pdf;
import com.chen.brand.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PdfServiceImp implements PdfService{

    @Autowired
    private PdfMapper pdfMapper;

    public void insert(Pdf pdf){
        pdfMapper.insert(pdf);
    }

    public void update(Pdf pdf){
        pdfMapper.update(pdf);
    }

    public void delete(Long id){
        pdfMapper.delete(id);
    }

    public Pdf findOne(Long id){
        return pdfMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data = new HashMap<>();
        data.put("total", pdfMapper.count(areaCode, sampleName, status, userId));
        data.put("list", pdfMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        return data;
    }

    public boolean isExist(Long id){
        return pdfMapper.isExist(id) > 0;
    }

    public Pdf findByUserIdAndBrandId(Long userId, Long brandId){
        return pdfMapper.findByUserIdAndBrandId(userId, brandId);
    }
}
