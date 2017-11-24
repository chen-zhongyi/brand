package com.chen.brand.mapper;

import com.chen.brand.model.TableGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TableGroupMapper {

    TableGroup findOne(@Param("id") Long id);

    List<TableGroup> findAll();

}
