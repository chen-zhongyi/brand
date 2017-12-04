package com.chen.brand.sys;

import com.chen.brand.model.Api;
import com.chen.brand.model.Area;
import com.chen.brand.service.ApiService;
import com.chen.brand.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
public class SysData {

    private List<Api> apis;
    private Map<String, Area> areaMap;


    @Autowired
    private ApiService apiService;

    @Autowired
    private AreaService areaService;

    public void init(){
        loadApi();
        loadArea();
    }

    public void loadApi(){
        apis = apiService.findAll();
    }

    public Api getApi(String url){
        for(Api api : apis){
            if(api.getApi().equals(url) || url.startsWith(api.getApi()))    return api;
        }
        return null;
    }

    public void loadArea(){
        if(areaMap == null) areaMap = new HashMap<>();
        List<Area> areas = areaService.findAll();
        for(Area area : areas){
            areaMap.put(area.getCode(), area);
        }
    }

    public Area getArea(String areaCode){
        if(areaCode == null)    return null;
        return areaMap.get(areaCode);
    }

    public List<Area> getAllArea(){
        List<Area> areas = new ArrayList<>();
        for(String areaCode : areaMap.keySet()){
            areas.add(areaMap.get(areaCode));
        }
        return areas;
    }

}
