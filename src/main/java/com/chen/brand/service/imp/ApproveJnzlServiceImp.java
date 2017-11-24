package com.chen.brand.service.imp;

import com.chen.brand.mapper.ApproveJnzlMapper;
import com.chen.brand.model.ApproveJnzl;
import com.chen.brand.service.ApproveJnzlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApproveJnzlServiceImp implements ApproveJnzlService{

    @Autowired
    private ApproveJnzlMapper jnzlMapper;

    public Long insert(ApproveJnzl jnzl){
        jnzlMapper.insert(jnzl);
        return jnzl.getId();
    }

    public void update(ApproveJnzl jnzl){
        jnzlMapper.update(jnzl);
    }

    public void delete(Long id){
        jnzlMapper.delete(id);
    }

    public ApproveJnzl findOne(Long id){
        return jnzlMapper.findOne(id);
    }

    public Map<String, Object> findAll(String areaCode, String sampleName, Long status, Long userId, int pageNumber, int pageSize){
        Map<String, Object> data =  new HashMap<>();
        data.put("list", jnzlMapper.findAll(areaCode, sampleName, status, userId, (pageNumber - 1) * pageSize, pageSize));
        data.put("total", jnzlMapper.count(areaCode, sampleName, status, userId));
        return data;
    }

    public boolean isExist(Long id){
        return jnzlMapper.isExist(id) > 0;
    }
}
