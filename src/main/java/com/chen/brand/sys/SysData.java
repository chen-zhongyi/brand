package com.chen.brand.sys;

import com.chen.brand.mapper.ApiMapper;
import com.chen.brand.mapper.AreaMapper;
import com.chen.brand.mapper.RoleMapper;
import com.chen.brand.model.Api;
import com.chen.brand.model.Area;
import com.chen.brand.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("singleton")
public class SysData {

    private List<Api> apis;
    private Map<String, Area> areaMap;
    private Map<String, Role> roleMap;


    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private RoleMapper roleMapper;

    public void init(){
        loadApi();
        loadArea();
        loadRole();
    }

    public void loadApi(){
        apis = apiMapper.findAll();
    }

    public Api getApi(String url){
        for(Api api : apis){
            if(api.getApi().equals(url) || url.startsWith(api.getApi()))    return api;
        }
        return null;
    }

    public void loadArea(){
        if(areaMap == null) areaMap = new HashMap<>();
        List<Area> areas = areaMapper.findAll();
        for(Area area : areas){
            areaMap.put(area.getCode(), area);
        }
    }

    public Area getArea(String areaCode){
        if(areaCode == null)    return null;
        return areaMap.get(areaCode);
    }

    public Area getAreaByName(String areaName) {
        Iterator<Area> areaIterator = areaMap.values().iterator();
        while (areaIterator.hasNext()) {
            Area area = areaIterator.next();
            if (areaName.contains(area.getName()))  return area;
        }
        return null;
    }

    public List<Area> getAllArea(){
        List<Area> areas = new ArrayList<>();
        for(String areaCode : areaMap.keySet()){
            areas.add(areaMap.get(areaCode));
        }
        return areas;
    }

    public void loadRole(){
        if(roleMap == null) roleMap = new HashMap<>();
        List<Role> roles = roleMapper.findAll();
        for(Role role : roles){
            roleMap.put(role.getCode(), role);
        }
    }

    public Role findByCode(String code){
        if(code == null)    return null;
        return roleMap.get(code);
    }

    public Role findById(Long id){
        if(id == null)  return null;
        for(String code : roleMap.keySet()){
            Role role = roleMap.get(code);
            if(role.getId() == id)  return role;
        }
        return null;
    }
}
