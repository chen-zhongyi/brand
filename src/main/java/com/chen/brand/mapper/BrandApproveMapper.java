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

    int update(BrandApprove brandApprove);

    BrandApprove findOne(@Param("id") Long id);

    int delete(@Param("id") Long id);

    List<BrandApprove> findAll(@Param("sampleName") String sampleName,
                               @Param("brandName") String brandName,
                               @Param("areaCode") String areaCode,
                               @Param("sampleId") Long sampleId,
                               @Param("brandId") Long brandId,
                               @Param("start") int start,
                               @Param("pageSize") int pageSize);

    List<BrandApprove> findByBrandId(@Param("brandId") Long brandId);

    BrandApprove findByBrandIdAndYear(@Param("brandId") Long brandId,
                                      @Param("year") String year);
}
