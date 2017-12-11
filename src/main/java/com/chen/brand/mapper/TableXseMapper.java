package com.chen.brand.mapper;

import com.chen.brand.model.TableXse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface TableXseMapper {

    int insert(TableXse table);

    int update(TableXse table);

    List<TableXse> findByRecordId(@Param("recordId") Long recordId);

    int isExist(@Param("recordId") Long recordId, @Param("brandId") Long brandId);

    List<Map<String, Object>> findAll(@Param("areaCode") String areaCode,
                                      @Param("sampleId") Long sampleId,
                                      @Param("planRound")Date planRound,
                                      @Param("start") int start,
                                      @Param("pageSize") int pageSize);

    int countAll(@Param("areaCode") String areaCode,
                 @Param("sampleId") Long sampleId,
                 @Param("planRound")Date planRound);
}
