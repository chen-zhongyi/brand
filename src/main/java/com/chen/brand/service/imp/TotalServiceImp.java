package com.chen.brand.service.imp;

import com.chen.brand.mapper.TotalMapper;
import com.chen.brand.model.Total;
import com.chen.brand.service.TotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TotalServiceImp implements TotalService {

    @Autowired
    private TotalMapper totalMapper;

    public Total insert(Total total){
        totalMapper.insert(total);
        return total;
    }

    public void update(Total total){
        totalMapper.update(total);
    }

    public Total findOne(Long id){
        return totalMapper.findOne(id);
    }

    public Map<String, Object> findAll(Long userId, String areaCode, int pageNumber, int pageSize){
        Map<String, Object> data = new HashMap<>();
        data.put("total", totalMapper.countAll(userId, areaCode));
        data.put("list", totalMapper.findAll(userId, areaCode, (pageNumber - 1) * pageSize, pageSize));
        return data;
    }

    public int countAll(Long userId, String areaCode){
        return totalMapper.countAll(userId, areaCode);
    }

    public Boolean isExist(Long id){
        return totalMapper.isExist(id) > 0;
    }

    public Total findByUserId(Long id){
        return totalMapper.findByUserId(id);
    }
}
