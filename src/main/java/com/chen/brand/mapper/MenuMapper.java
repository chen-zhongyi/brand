package com.chen.brand.mapper;

import com.chen.brand.model.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MenuMapper {

    int insert(Menu menu);

    int update(Menu menu);

    List<Menu> findAll(@Param("type") String type);

    int isExist(@Param("id") Long id);

    int changeStatus(@Param("id") Long id, @Param("status") Boolean status);

    Menu findOne(@Param("id") Long id);

    int delete(@Param("id") Long id);
}
