package com.chen.brand.service.imp;

import com.chen.brand.mapper.ApiMapper;
import com.chen.brand.model.Api;
import com.chen.brand.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiServiceImp implements ApiService{

    @Autowired
    private ApiMapper apiMapper;

    public void insert(Api api){
        apiMapper.insert(api);
    }

    public void update(Api api){
        apiMapper.update(api);
    }

    public void delete(Long id){
        apiMapper.delete(id);
    }

    public Api findOne(Long id){
        return apiMapper.findOne(id);
    }

    public List<Api> findAll(){
        return apiMapper.findAll();
    }

    public Boolean isExist(Long id){
        return apiMapper.isExist(id) > 0;
    }

    public Api findByApi(String api){
        return apiMapper.findByApi(api);
    }
}
