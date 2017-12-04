package com.chen.brand.service.imp;

import com.chen.brand.mapper.TableBaseMapper;
import com.chen.brand.model.TableBase;
import com.chen.brand.service.TableBaseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableBaseServerImp implements TableBaseServer{

    @Autowired
    private TableBaseMapper baseMapper;

    @Override
    public TableBase findByUserIdAndYearAndStatus(Long userId, String year, Long status) {
        return baseMapper.findByUserIdAndYearAndStatus(userId, year, status);
    }
}
