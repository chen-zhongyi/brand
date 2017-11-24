package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.http.request.sys.SysRequest;
import com.chen.brand.model.Sys;
import com.chen.brand.service.SysService;
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
@Api(value = "api -- sysController", description = "系统管理接口")
@RequestMapping( value = "/api/systems")
public class SysController extends BaseController{

    @Autowired
    private SysService sysService;

    @ApiOperation(value = "创建系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRequest", value = "请求实体类", dataType = "SysRequest", paramType = "body")
    })
    @PostMapping("")
    public Map<String, Object> insert(@RequestBody @Valid SysRequest sysRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(sysService.isExistCode(sysRequest.getCode())){
            return createResponse(Constant.FAIL, "code已存在", null);
        }
        Sys sys = new Sys();
        sys.setCode(sysRequest.getCode());
        sys.setDescription(sysRequest.getDescription());
        sys.setStatus(true);
        Long id = sysService.insert(sys);
        return createResponse(Constant.SUCCESS, "成功", sysService.findOne(id));
    }

    @ApiOperation(value = "根据id查询系统信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", paramType = "path")
    })
    @GetMapping("/{id}")
    public Map<String, Object> findOne(@PathVariable Long id){
        if(id == null || sysService.isExist(id) == false){
            return createResponse(Constant.FAIL, "id不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", sysService.findOne(id));
    }

    @ApiOperation(value = "查询系统信息列表")
    @GetMapping("")
    public Map<String, Object> findAll(){
        return createResponse(Constant.SUCCESS, "成功", sysService.findAll());
    }

    @ApiOperation(value = "禁用启用系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", paramType = "path")
    })
    @PutMapping("/changeStatus/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id){
        if(id == null || sysService.isExist(id) == false){
            return createResponse(Constant.FAIL, "id不存在", null);
        }
        Sys sys = sysService.findOne(id);
        sys.setStatus(sys.getStatus() == true ? false : true);
        sysService.changeStatus(sys.getId(), sys.getStatus());
        return createResponse(Constant.SUCCESS, "成功", sysService.findOne(id));
    }
}
