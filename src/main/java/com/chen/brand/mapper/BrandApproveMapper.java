package com.chen.brand.mapper;

import com.chen.brand.model.BrandApprove;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BrandApproveMapper {

    int insert(BrandApprove brandApprove);

    List<BrandApprove> findAll();

    List<BrandApprove> findByBrandId(@Param("brandId") Long brandId);
}
