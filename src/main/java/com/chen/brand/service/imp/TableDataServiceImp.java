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

    public Object findAll(Long tableId, String areaCode, Long sampleId, Date planRound){
        if(tableId == Constant.BASE){
            return baseMapper.findAll(areaCode, sampleId, planRound);
        }else if(tableId == Constant.JYGM){
            return jygmMapper.findAll(areaCode, sampleId, planRound);
        }else if(tableId == Constant.XSE){
            return xseMapper.findAll(areaCode, sampleId, planRound);
        }else if(tableId == Constant.QKDC){
            return qkdcMapper.findAll(areaCode, sampleId, planRound);
        }
        return null;
    }
}
