package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.http.request.table.TableInsert;
import com.chen.brand.model.Table;
import com.chen.brand.service.TableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping( value = "/api/tables")
@Api(value = "api -- tableController", description = "报表管理接口")
public class TableController extends BaseController{

    @Autowired
    private TableService tableService;

    @ApiOperation( value = "创建报表")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "tableRequest", value = "请求体类型", paramType = "body", dataType = "TableInsert")
    })
    @PostMapping( value = "")
    public Map<String, Object> insert(@RequestBody @Valid TableInsert tableRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        Table table = new Table();
        table.setName(tableRequest.getName());
        table.setGroupId(tableRequest.getGroupId());
        table.setTheNo(tableRequest.getTheNo());
        table.setGuide(tableRequest.getGuide());
        table.setValiGuide(tableRequest.getValiGuide());
        table.setOrderNo(tableRequest.getOrderBy());
        table.setStatus(true);
        Long id = tableService.insert(table);
        return createResponse(Constant.SUCCESS, "成功", tableService.findOne(id));
    }

    @ApiOperation( value = "修改报表")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "报表id", paramType = "path", dataType = "Long"),
            @ApiImplicitParam( name = "tableRequest", value = "请求体类型", paramType = "body", dataType = "TableInsert")
    })
    @PutMapping( value = "/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid TableInsert tableRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if(tableService.isExist(id) == false){
            return createResponse(Constant.FAIL, "报表不存在", null);
        }
        Table table = new Table();
        table.setId(id);
        table.setName(tableRequest.getName());
        table.setGroupId(tableRequest.getGroupId());
        table.setTheNo(tableRequest.getTheNo());
        table.setGuide(tableRequest.getGuide());
        table.setValiGuide(tableRequest.getValiGuide());
        table.setOrderNo(tableRequest.getOrderBy());
        table.setStatus(true);
        tableService.update(table);
        return createResponse(Constant.SUCCESS, "成功", tableService.findOne(id));
    }

    @ApiOperation( value = "根据id获取报表")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "报表id", paramType = "path", dataType = "Long")
    })
    @GetMapping( value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id){
        if(tableService.isExist(id) == false){
            return createResponse(Constant.FAIL, "报表不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", tableService.findOne(id));
    }

    @ApiOperation( value = "获取报表列表")
    @GetMapping( value = "")
    public Map<String, Object> findAll(){
        return createResponse(Constant.SUCCESS, "成功", tableService.findAll());
    }

    @ApiOperation( value = "启用禁用报表")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "报表id", paramType = "path", dataType = "Long")
    })
    @PutMapping( value = "/changeStatus/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id){
        if(tableService.isExist(id) == false){
            return createResponse(Constant.FAIL, "报表不存在", null);
        }
        Table table = tableService.findOne(id);
        tableService.changeStatus(id, table.getStatus() == true ? false : true);
        return createResponse(Constant.SUCCESS, "成功", tableService.findOne(id));
    }
}
