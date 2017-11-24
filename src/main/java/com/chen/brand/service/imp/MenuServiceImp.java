package com.chen.brand.service.imp;

import com.chen.brand.mapper.MenuMapper;
import com.chen.brand.model.Menu;
import com.chen.brand.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImp implements MenuService{

    @Autowired
    private MenuMapper menuMapper;

    public Long insert(Menu menu){
        menuMapper.insert(menu);
        return menu.getId();
    }

    public void update(Menu menu){
        menuMapper.update(menu);
    }

    public List<Menu> findAll(String type){
        return menuMapper.findAll(type);
    }

    public boolean isExist(Long id){
        return menuMapper.isExist(id) > 0;
    }

    public Menu findOne(Long id){
        return menuMapper.findOne(id);
    }

    public void delete(Long id){
        menuMapper.delete(id);
    }

    public void changeStatus(Long id, Boolean stauts){
        menuMapper.changeStatus(id, stauts);
    }
}
