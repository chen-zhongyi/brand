package com.chen.brand.service.imp;

import com.chen.brand.Constant;
import com.chen.brand.mapper.TableBaseMapper;
import com.chen.brand.mapper.TableJygmMapper;
import com.chen.brand.mapper.TableQkdcMapper;
import com.chen.brand.mapper.TableXseMapper;
import com.chen.brand.service.TableDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TableDataServiceImp implements TableDataService{

    @Autowired
    private TableBaseMapper baseMapper;

    @Autowired
    private TableJygmMapper jygmMapper;

    @Autowired
    private TableXseMapper xseMapper;

    @Autowired
    private TableQkdcMapper qkdcMapper;

    public Map<String, Object> findAll(Long tableId, String areaCode, Long sampleId, Date planRound, int pageNumber, int pageSize){
        Map<String, Object> data = new HashMap<>();
        if(tableId == Constant.BASE){
            data.put("total", baseMapper.countAll(areaCode, sampleId, planRound));
            data.put("list", baseMapper.findAll(areaCode, sampleId, planRound, (pageNumber - 1) * pageSize, pageSize));
        }else if(tableId == Constant.JYGM){
            data.put("total", jygmMapper.countAll(areaCode, sampleId, planRound));
            data.put("list", jygmMapper.findAll(areaCode, sampleId, planRound, (pageNumber - 1) * pageSize, pageSize));
        }else if(tableId == Constant.XSE){
            data.put("total", xseMapper.countAll(areaCode, sampleId, planRound));
            data.put("list", xseMapper.findAll(areaCode, sampleId, planRound, (pageNumber - 1) * pageSize, pageSize));
        }else if(tableId == Constant.QKDC){
            data.put("total", qkdcMapper.countAll(areaCode, sampleId, planRound));
            data.put("list", qkdcMapper.findAll(areaCode, sampleId, planRound, (pageNumber - 1) * pageSize, pageSize));
        }
        return data;
    }
}
