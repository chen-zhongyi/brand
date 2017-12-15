package com.chen.brand.mapper;

import com.chen.brand.model.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BrandMapper {

    int insert(Brand brand);

    int update(Brand brand);

    int delete(@Param("id") Long id);

    Brand findOne(@Param("id") Long id);

    List<Brand> findAll(@Param("areaCode") String areaCode, @Param("sampleId") Long sampleId, @Param("ppmc") String ppmc, @Param("status") Long status, @Param("start") int start, @Param("pageSize") int pageSize);

    int count(@Param("areaCode") String areaCode, @Param("sampleId") Long sampleId, @Param("ppmc") String ppmc, @Param("status") Long status);

    int approve(@Param("id") Long id, @Param("status") Long status);

    int isExist(@Param("id") Long id);

    List<Brand> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Long status);
}
