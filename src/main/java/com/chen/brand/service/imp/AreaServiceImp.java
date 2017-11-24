package com.chen.brand.service.imp;

import com.chen.brand.mapper.AreaMapper;
import com.chen.brand.model.Area;
import com.chen.brand.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImp implements AreaService{

    @Autowired
    private AreaMapper areaMapper;

    public void insert(Area area){
        areaMapper.insert(area);
    }

    public void update(Area area){
        areaMapper.update(area);
    }

    public void delete(String code){
        areaMapper.delete(code);
    }

    public Area findOne(String code){
        return areaMapper.findOne(code);
    }

    public List<Area> findAll(){
        return areaMapper.findAll();
    }

    public boolean isExist(String code){
        return areaMapper.isExist(code) > 0;
    }
}
