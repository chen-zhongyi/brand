package com.chen.brand.mapper;

import com.chen.brand.model.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Mapper
@Component
public interface LogMapper {

    int insert(Log log);

    int delete(Long id);

    int deleteAll();

    Log findOne(@Param("id") Long id);

    List<Log> findAll(@Param("userName") String userName,
                      @Param("startTime") Date startTime,
                      @Param("endTime") Date endTime,
                      @Param("type") Long type,
                      @Param("start") int start,
                      @Param("pageSize") int pageSize);


    int isExist(@Param("id") Long id);

}
