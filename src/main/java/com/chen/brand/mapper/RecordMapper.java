package com.chen.brand.mapper;

import com.chen.brand.model.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Mapper
@Component
public interface RecordMapper {

    int insert(Record record);

    int update(Record record);

    Record findOne(@Param("id") Long id);

    int isExist(@Param("id") Long id);

    List<Record> findAll(@Param("areaCode") String areaCode, @Param("sampleId") Long sampleId, @Param("sampleName") String sampleName, @Param("planRound")Date planRound, @Param("tableId") Long tableId, @Param("start") int start, @Param("pageSize") int pageSize);

    int count(@Param("areaCode") String areaCode, @Param("sampleId") Long sampleId, @Param("sampleName") String sampleName, @Param("planRound")Date planRound, @Param("tableId") Long tableId);

    Long findByTableIdAndSampleIdAndPlanId(@Param("tableId") Long tableId, @Param("sampleId") Long sampleId, @Param("planId") Long planId);
}
