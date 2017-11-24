package com.chen.brand.mapper;

import com.chen.brand.model.TableQkdc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Mapper
@Component
public interface TableQkdcMapper {

    int insert(TableQkdc table);

    int update(TableQkdc table);

    TableQkdc findByRecordId(@Param("recordId") Long recordId);

    List<TableQkdc> findAll(@Param("areaCode") String areaCode,
                            @Param("sampleId") Long sampleId,
                            @Param("planRound")Date planRound);
}
