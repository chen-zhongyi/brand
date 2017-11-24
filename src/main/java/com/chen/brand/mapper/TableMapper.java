package com.chen.brand.mapper;

import com.chen.brand.model.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TableMapper {

    int insert(Table table);

    int update(Table table);

    Table findOne(@Param("id") Long id);

    List<Table> findAll();

    int isExist(@Param("id") Long id);

    int changeStatus(@Param("id") Long id, @Param("status") Boolean status);
}
