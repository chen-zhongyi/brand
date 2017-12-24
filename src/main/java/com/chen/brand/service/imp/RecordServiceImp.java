package com.chen.brand.service.imp;

import com.chen.brand.Enum.TableType;
import com.chen.brand.mapper.*;
import com.chen.brand.model.*;
import com.chen.brand.service.RecordService;
import com.chen.brand.util.TimeUtils;
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
        StringBuffer tableRecords = new StringBuffer("");
        if(record.getTableId() == TableType.BASE.code()){
            for(TableBase base : bases) {
                base.setRecordId(record.getId());
                TableBase temp = baseMapper.findByYearAndRecordId(TimeUtils.getYear(base.getRound()), base.getRecordId());
                if(temp != null){
                    continue;
                }
                baseMapper.insert(base);
                if(tableRecords.toString().length() == 0)   tableRecords.append(base.getId());
                else
                    tableRecords.append("," + base.getId());
            }
        }else if(record.getTableId() == TableType.JYGM.code()){
            jygm.setRecordId(record.getId());
            jygmMapper.insert(jygm);
            tableRecords.append(jygm.getId());
        }else if(record.getTableId() == TableType.XSE.code()){
            for(Long xseId : xses.keySet()) {
                TableXse xse = xses.get(xseId);
                xse.setRecordId(record.getId());
                xse.setBrandId(xseId);
                if(xseMapper.isExist(xse.getRecordId(), xse.getBrandId()) > 0){
                    continue;
                }
                xseMapper.insert(xse);
                if(tableRecords.toString().length() == 0)   tableRecords.append(xse.getId());
                else
                    tableRecords.append("," + xse.getId());
            }
        }else if(record.getTableId() == TableType.QKDC.code()){
            qkdc.setRecordId(record.getId());
            qkdcMapper.insert(qkdc);
            tableRecords.append(qkdc.getId());
        }
        record.setTableReportId(tableRecords.toString());
        recordMapper.update(record);
        return record.getId();
    }

    public void update(Record record, List<TableBase> bases, TableJygm jygm, Map<Long, TableXse> xses, TableQkdc qkdc){
        StringBuffer tableRecords = record.getTableReportId() == null ? null : new StringBuffer(record.getTableReportId());
        if(record.getTableId() == TableType.BASE.code()){
            for(TableBase base : bases) {
                TableBase temp = baseMapper.findByYearAndRecordId(TimeUtils.getYear(base.getRound()), base.getRecordId());
                if(temp == null){
                    baseMapper.insert(base);
                    if(tableRecords.toString().length() == 0)   tableRecords.append(base.getId());
                    else
                        tableRecords.append("," + base.getId());
                    continue;
                }
                baseMapper.update(base);
            }
        }else if(record.getTableId() == TableType.JYGM.code()){
            jygmMapper.update(jygm);
        }else if(record.getTableId() == TableType.XSE.code()){
            for(Long id : xses.keySet()) {
                TableXse xse = xses.get(id);
                if(xseMapper.isExist(xse.getRecordId(), id) == 0){
                    xseMapper.insert(xse);
                    if(tableRecords.toString().length() == 0)   tableRecords.append(xse.getId());
                    else
                        tableRecords.append("," + xse.getId());
                    continue;
                }
                xseMapper.update(xse);
            }
        }else if(record.getTableId() == TableType.QKDC.code()){
            qkdcMapper.update(qkdc);
        }
        record.setTableReportId(tableRecords == null ? null : tableRecords.toString());
        recordMapper.update(record);
    }

    public Map<String, Object> findOne(Long id){
        Map<String, Object> data = new HashMap<>();
        Record record = recordMapper.findOne(id);
        data.put("record", record);
        Object object = null;
        if(record.getTableId() == TableType.BASE.code()){
            object = baseMapper.findByRecordId(id);
        }else if(record.getTableId() == TableType.JYGM.code()){
            object = jygmMapper.findByRecordId(id);
        }else if(record.getTableId() == TableType.XSE.code()){
            Map<String, TableXse> xses = new HashMap<>();
            for(TableXse xse : xseMapper.findByRecordId(id)){
                xses.put(String.valueOf(brandMapper.findOne(xse.getBrandId()).getId()), xse);
            }
            object = xses;
        }else if(record.getTableId() == TableType.QKDC.code()){
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
