package com.chen.brand.service.imp;

import com.chen.brand.mapper.RoleMapper;
import com.chen.brand.model.Role;
import com.chen.brand.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    public Long insert(Role role){
        roleMapper.insert(role);
        return role.getId();
    }

    public void update (Role role){
        roleMapper.update(role);
    }

    public Role findOne(Long id){
        return roleMapper.findOne(id);
    }

    public List<Role> findAll(){
        return roleMapper.findAll();
    }

    public boolean isExist(Long id){
        return roleMapper.isExist(id) > 0;
    }

    public boolean isExistCode(String code){
        return roleMapper.isExistCode(code) > 0;
    }

    public void changeStatus(Long id, Boolean status){
        roleMapper.changeStatus(id, status);
    }
}
