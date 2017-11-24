package com.chen.brand.service.imp;

import com.chen.brand.mapper.TableMapper;
import com.chen.brand.model.Table;
import com.chen.brand.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImp implements TableService{

    @Autowired
    private TableMapper tableMapper;

    public Long insert(Table table){
        tableMapper.insert(table);
        return table.getId();
    }

    public void update(Table table){
        tableMapper.update(table);
    }

    public Table findOne(Long id){
        return tableMapper.findOne(id);
    }

    public List<Table> findAll(){
        return tableMapper.findAll();
    }

    public boolean isExist(Long id){
        return tableMapper.isExist(id) > 0;
    }

    public void changeStatus(Long id, Boolean status){
        tableMapper.changeStatus(id, status);
    }
}
