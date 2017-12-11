package com.chen.brand.service;

import java.sql.Date;
import java.util.Map;

public interface TableDataService {

    Map<String, Object> findAll(Long tableId, String areaCode, Long sampleId, Date planRound, int pageNumber, int pageSize);
}
