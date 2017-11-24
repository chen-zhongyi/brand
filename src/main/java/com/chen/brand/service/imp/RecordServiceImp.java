package com.chen.brand.service.imp;

import com.chen.brand.mapper.*;
import com.chen.brand.model.*;
import com.chen.brand.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordServiceImp implements RecordService{

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private TableBaseMapper baseMapper;

    @Autowired
    private TableJygmMapper jygmMapper;

    @Autowired
    private TableXseMapper xseMapper;

    @Autowired
    private TableQkdcMapper qkdcMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Transactional
    public Long insert(Record record, List<TableBase> bases, TableJygm jygm, Map<Long, TableXse> xses, TableQkdc qkdc){
        recordMapper.insert(record);
        if(record.getTableId() == 1L){
            for(TableBase base : bases) {
                base.setRecordId(record.getId());
                baseMapper.insert(base);
            }
        }else if(record.getTableId() == 2L){
            jygm.setRecordId(record.getId());
            jygmMapper.insert(jygm);
        }else if(record.getTableId() == 3L){
            for(Long xseId : xses.keySet()) {
                TableXse xse = xses.get(xseId);
                xse.setRecordId(record.getId());
                xse.setBrandId(xseId);
                xseMapper.insert(xse);
            }
        }else if(record.getTableId() == 4L){
            qkdc.setRecordId(record.getId());
            qkdcMapper.insert(qkdc);
        }
        return record.getId();
    }

    public void update(Record record, List<TableBase> bases, TableJygm jygm, Map<Long, TableXse> xses, TableQkdc qkdc){
        recordMapper.update(record);
        if(record.getTableId() == 1L){
            for(TableBase base : bases) {
                baseMapper.update(base);
            }
        }else if(record.getTableId() == 2L){
            jygmMapper.update(jygm);
        }else if(record.getTableId() == 3L){
            for(Long id : xses.keySet()) {
                TableXse xse = xses.get(id);
                if(xseMapper.isExist(xse.getRecordId(), id) == 0){
                    xseMapper.insert(xse);
                    continue;
                }
                xseMapper.update(xse);
            }
        }else if(record.getTableId() == 4L){
            qkdcMapper.update(qkdc);
        }
    }

    public Map<String, Object> findOne(Long id){
        Map<String, Object> data = new HashMap<>();
        Record record = recordMapper.findOne(id);
        data.put("record", record);
        Object object = null;
        if(record.getTableId() == 1L){
            object = baseMapper.findByRecordId(id);
        }else if(record.getTableId() == 2L){
            object = jygmMapper.findByRecordId(id);
        }else if(record.getTableId() == 3L){
            Map<String, TableXse> xses = new HashMap<>();
            for(TableXse xse : xseMapper.findByRecordId(id)){
                xses.put(String.valueOf(brandMapper.findOne(xse.getBrandId()).getId()), xse);
            }
            object = xses;
        }else if(record.getTableId() == 4L){
            object = qkdcMapper.findByRecordId(id);
        }
        data.put("table", object);
        return data;
    }

    public Map<String, Object> findAll(String areaCode, Long sampleId, String sampleName, Date planRound, Long tableId, int pageNumber, int pageSize){
        Map<String, Object> data = new HashMap<>();
        data.put("list", recordMapper.findAll(areaCode, sampleId, sampleName, planRound, tableId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", recordMapper.count(areaCode, sampleId, sampleName, planRound, tableId));
        return data;
    }

    public boolean isExist(Long id){
        return recordMapper.isExist(id) > 0;
    }

    public Long findByTableIdAndSampleIdAndPlanId(Long tableId, Long sampleId, Long planId){
        return recordMapper.findByTableIdAndSampleIdAndPlanId(tableId, sampleId, planId);
    }

}
