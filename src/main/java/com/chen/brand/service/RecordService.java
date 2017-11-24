package com.chen.brand.service;

import com.chen.brand.model.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface RecordService {

    Long insert(Record record, List<TableBase> bases, TableJygm jygm, Map<Long, TableXse> xses, TableQkdc qkdc);

    void update(Record record, List<TableBase> bases, TableJygm jygm, Map<Long, TableXse> xse, TableQkdc qkdc);

    Map<String, Object> findOne(Long id);

    Map<String, Object> findAll(String areaCode, Long sampleId, String sampleName, Date planRound, Long tableId, int pageNumber, int pageSize);

    boolean isExist(Long id);

    Long findByTableIdAndSampleIdAndPlanId(Long tableId, Long sampleId, Long planId);
}
