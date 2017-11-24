package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.http.request.api.ApiRequest;
import com.chen.brand.http.request.api.ApiUpdate;
import com.chen.brand.model.Api;
import com.chen.brand.service.ApiService;
import com.chen.brand.service.SysService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/apis")
@io.swagger.annotations.Api( value = "api -- ApiController", description = "API管理")
public class ApiController extends BaseController{

    @Autowired
    private ApiService apiService;

    @Autowired
    private SysService sysService;

    @ApiOperation(value = "创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "ApiRequest", paramType = "Body")
    })
    @PostMapping("")
    public Map<String, Object> insert(@RequestBody ApiRequest request, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "失败", createErrors(errors));
        }
        if(request.getSystemCode().equals("sys") || request.getSystemCode().equals("public") || sysService.isExistCode(request.getSystemCode())){

        }else
            return createResponse(Constant.FAIL, "系统code不存在", null);
        Api api = new Api();
        api.setApi(request.getApi());
        api.setSystemCode(request.getSystemCode());
        api.setStatus(true);
        apiService.insert(api);
        return createResponse(Constant.SUCCESS, "成功", api);
    }

    @ApiOperation(value = "更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "ApiRequest", paramType = "Body")
    })
    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody ApiUpdate request, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "失败", createErrors(errors));
        }
        if(apiService.isExist(id) == false){
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        if(request.getSystemCode().equals("sys") || request.getSystemCode().equals("public") || sysService.isExistCode(request.getSystemCode())){

        }else
            return createResponse(Constant.FAIL, "系统code不存在", null);
        Api api = new Api();
        api.setId(id);
        api.setSystemCode(request.getSystemCode());
        apiService.update(api);
        return createResponse(Constant.SUCCESS, "成功", apiService.findOne(id));
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "int", paramType = "path")
    })
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Long id){
        if(apiService.isExist(id) == false){
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        apiService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation(value = "根据ID查询记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "int", paramType = "path")
    })
    @GetMapping("/{id}")
    public Map<String, Object> findOne(@PathVariable Long id){
        if(apiService.isExist(id) == false){
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", apiService.findOne(id));
    }

    @ApiOperation(value = "查询列表")
    @GetMapping("")
    public Map<String, Object> findAll(){
        return createResponse(Constant.SUCCESS, "成功", apiService.findAll());
    }
}
