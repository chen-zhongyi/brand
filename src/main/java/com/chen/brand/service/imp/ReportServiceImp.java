package com.chen.brand.service.imp;

import com.chen.brand.mapper.ReportMapper;
import com.chen.brand.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportServiceImp implements ReportService{

    @Autowired
    private ReportMapper reportMapper;

    public Map<String, Object> findAll(String areaCode, Long sampleId, String sampleName, Date planRound, Long tableId, Long status, int pageNumber, int pageSize){
        Map<String, Object> data = new HashMap<>();
        data.put("list", reportMapper.findAll(areaCode, sampleId, sampleName, planRound, tableId, status,(pageNumber - 1) * pageSize, pageSize));
        data.put("total", reportMapper.count(areaCode, sampleId, sampleName, planRound, tableId, status));
        return data;
    }
}
