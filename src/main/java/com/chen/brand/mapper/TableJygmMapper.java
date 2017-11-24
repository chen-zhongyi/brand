package com.chen.brand.mapper;

import com.chen.brand.model.TableJygm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Mapper
@Component
public interface TableJygmMapper {

    int insert(TableJygm table);

    int update(TableJygm table);

    TableJygm findByRecordId(@Param("recordId") Long recordId);

    List<TableJygm> findAll(@Param("areaCode") String areaCode,
                            @Param("sampleId") Long sampleId,
                            @Param("planRound")Date planRound);
}
