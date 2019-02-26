package com.chen.brand.service.imp;

import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.mapper.ApprovalGxqyMapper;
import com.chen.brand.model.ApprovalGxqy;
import com.chen.brand.service.ApprovalGxqyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApprovalGxqyServiceImp implements ApprovalGxqyService {

    @Autowired
    private ApprovalGxqyMapper jnzlMapper;

    public Long insert(ApprovalGxqy jnzl){
        jnzlMapper.insert(jnzl);
        return jnzl.getId();
    }

    public void update(ApprovalGxqy jnzl){
        jnzlMapper.update(jnzl);
    }

    public void delete(Long id){
        jnzlMapper.delete(id);
    }

    public ApprovalGxqy findOne(Long id){
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

    public int[] total(Long userId){
        int[] ans = new int[2];
        ans[0] = jnzlMapper.count(null, null, null, userId);
        ans[1] = jnzlMapper.count(null, null, ApproveStatus.FinalApprovePass.getStatus(), userId);
        return ans;
    }

    public List<ApprovalGxqy> findByUserIdAndStatus(Long userId, Long status){
        return jnzlMapper.findByUserIdAndStatus(userId, status);
    }
}
