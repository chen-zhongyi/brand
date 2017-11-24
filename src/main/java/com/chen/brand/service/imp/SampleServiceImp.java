package com.chen.brand.service.imp;

import com.chen.brand.mapper.SampleMapper;
import com.chen.brand.mapper.ZsMapper;
import com.chen.brand.model.Sample;
import com.chen.brand.model.Zs;
import com.chen.brand.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class SampleServiceImp implements SampleService{

    @Autowired
    private SampleMapper sampleMapper;

    @Autowired
    private ZsMapper zsMapper;

    public Long insert(Sample sample, List<Zs> bgzs){
        sampleMapper.insert(sample);
        for(Zs zs : bgzs){
            zs.setSampleId(sample.getId());
            zsMapper.insert(zs);
        }
        return sample.getId();
    }

    public void update(Sample sample, List<Zs> bgzs){
        sampleMapper.update(sample);
        for(Zs zs : bgzs){
            if(zsMapper.isExist(zs.getId()) == 0){
                zs.setSampleId(sample.getId());
                zsMapper.insert(zs);
            }else if(zsMapper.isExist(zs.getId()) > 0){
                zsMapper.update(zs);
            }
        }
    }

    public void delete(Long id){
        sampleMapper.delete(id);
    }

    public Map<String, Object> findOne(Long id){
        Sample sample = sampleMapper.findOne(id);
        Map<String, Object> data = new HashMap<>();
        data.put("sample", sample);
        data.put("bgzs", zsMapper.findAll(sample.getId()));
        return data;
    }

    public Map<String, Object> findAll(String areaCode, Long userId, String xydm, String qymc, int pageNumber, int pageSize){
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> temps = new LinkedList<>();
        List<Sample> samples = sampleMapper.findAll(areaCode, userId, xydm, qymc, (pageNumber - 1) * pageSize, pageSize);
        for(Sample sample : samples){
            Map<String, Object> temp = new HashMap<>();
            temp.put("sample", sample);
            temp.put("bgzs", zsMapper.findAll(sample.getId()));
            temps.add(temp);
        }
        data.put("list", temps);
        data.put("total", sampleMapper.count(areaCode, userId, xydm, qymc));
        return data;
    }

    public boolean isExist(Long id){
        return sampleMapper.isExist(id) > 0;
    }

    public void approve(Long id, Long status){
        sampleMapper.approve(id, status);
    }

    public Sample findByUserId(Long userId){
        return sampleMapper.findByUserId(userId);
    }

}
