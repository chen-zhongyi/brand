package com.chen.brand.service;

import java.sql.Date;

public interface TableDataService {

    Object findAll(Long tableId, String areaCode, Long sampleId, Date planRound);
}
