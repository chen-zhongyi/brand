package com.chen.brand.mapper;

import com.chen.brand.model.Pdf;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PdfMapper {

    int insert(Pdf pdf);

    int update(Pdf pdf);

    int delete(Long id);

    Pdf findOne(@Param("id") Long id);

    int isExist(@Param("id") Long id);

    List<Pdf> findAll(@Param("areaCode") String areaCode,
                      @Param("sampleName") String sampleName,
                      @Param("status") Long status,
                      @Param("userId") Long userId,
                      @Param("start") int start,
                      @Param("pageSize") int pageSize);

    int count(@Param("areaCode") String areaCode,
              @Param("sampleName") String sampleName,
              @Param("status") Long status,
              @Param("userId") Long userId);
}
