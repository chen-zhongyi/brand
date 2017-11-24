package com.chen.brand.mapper;

import com.chen.brand.model.TableBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Mapper
@Component
public interface TableBaseMapper {

    int insert(TableBase base);

    int update(TableBase base);

    List<TableBase> findByRecordId(@Param("recordId") Long recordId);

    List<TableBase> findAll(@Param("areaCode") String areaCode,
                            @Param("sampleId") Long sampleId,
                            @Param("planRound")Date planRound);
}
