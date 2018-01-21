package com.chen.brand.service;


import java.sql.Date;
import java.util.Map;

public interface ReportService {

    Map<String, Object> findAll(String areaCode, Long sampleId, String sampleName, Date planRound, Long tableId, Long status, int pageNumber, int pageSize);

}
