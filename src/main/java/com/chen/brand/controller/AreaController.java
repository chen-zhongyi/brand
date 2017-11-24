package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.http.request.Area.AreaInsert;
import com.chen.brand.http.request.Area.AreaUpdate;
import com.chen.brand.model.Area;
import com.chen.brand.service.AreaService;
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
@RequestMapping( value = "/api/areas")
@Api(value = "api -- areaController", description = "区县管理接口")
public class AreaController extends BaseController{

    @Autowired
    private AreaService areaService;

    @ApiOperation("创建区县")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "areaRequest", value = "区县请求实体类", paramType = "body", dataType = "AreaInsert")
    })
    @PostMapping( value = "")
    public Map<String, Object> insert(@RequestBody @Valid AreaInsert areaRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(areaService.isExist(areaRequest.getCode())){
            return createResponse(Constant.FAIL, "区县代码已存在", null);
        }
        Area area = new Area();
        area.setCode(areaRequest.getCode());
        area.setName(areaRequest.getName());
        area.setPcode(areaRequest.getPcode());
        area.setLevel(areaRequest.getLevel());
        area.setOrderNo(areaRequest.getOrderNo());
        areaService.insert(area);
        return createResponse(Constant.SUCCESS, "成功", area);
    }

    @ApiOperation("修改区县")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "code", value = "区县code", paramType = "path", dataType = "String"),
            @ApiImplicitParam( name = "areaRequest", value = "区县请求实体类", paramType = "body", dataType = "AreaUpdate")
    })
    @PutMapping( value = "/{code}")
    public Map<String, Object> update(@PathVariable String code, @RequestBody @Valid AreaUpdate areaRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(areaService.isExist(code) == false){
            return createResponse(Constant.FAIL, "区县代码不存在", null);
        }
        Area area = new Area();
        area.setCode(code);
        area.setName(areaRequest.getName());
        area.setPcode(areaRequest.getPcode());
        area.setLevel(areaRequest.getLevel());
        area.setOrderNo(areaRequest.getOrderNo());
        areaService.update(area);
        return createResponse(Constant.SUCCESS, "成功", areaService.findOne(code));
    }

    @ApiOperation("根据code获取区县信息")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "code", value = "区县code", paramType = "path", dataType = "String")
    })
    @GetMapping( value = "/{code}")
    public Map<String, Object> findOne(@PathVariable String code){
        if(areaService.isExist(code) == false){
            return createResponse(Constant.FAIL, "区县代码不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", areaService.findOne(code));
    }

    @ApiOperation("获取区县信息列表")
    @GetMapping( value = "")
    public Map<String, Object> findAll(){
        return createResponse(Constant.SUCCESS, "成功", areaService.findAll());
    }

    @ApiOperation("删除区县信息")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "code", value = "区县code", paramType = "path", dataType = "String")
    })
    @DeleteMapping( value = "/{code}")
    public Map<String, Object> delete(@PathVariable String code){
        if(areaService.isExist(code) == false){
            return createResponse(Constant.FAIL, "区县代码不存在", null);
        }
        areaService.delete(code);
        return createResponse(Constant.SUCCESS, "成功", null);
    }
}
