package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/logs")
@Api(value = "api -- LogController", description = "日志管理系统")
public class LogController extends BaseController{

    @Autowired
    private LogService logService;

    @ApiOperation("根据ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "path")
    })
    @GetMapping("/{id}")
    public Map<String, Object> findOne(@PathVariable Long id){
        if(logService.isExist(id) == false){
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", logService.findOne(id));
    }

    @ApiOperation("查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型0-异常，1-正常", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "string", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页，分页大小", dataType = "string", paramType = "query", defaultValue = "20"),
    })
    @GetMapping("")
    public Map<String, Object> findAll(@RequestParam(required = false) String userName,
                                       @RequestParam(required = false) Date startTime,
                                       @RequestParam(required = false) Date endTime,
                                       @RequestParam(required = false) Long type,
                                       @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                       @RequestParam(required = false, defaultValue = "20") int pageSize){
        return createResponse(Constant.SUCCESS, "成功", logService.findAll(userName, startTime, endTime, type, pageNumber, pageSize));
    }

    @ApiOperation("根据ID删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "path")
    })
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteOne(@PathVariable Long id){
        if(logService.isExist(id) == false){
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        logService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation("删除全部日志")
    @DeleteMapping("")
    public Map<String, Object> deleteAll(){
        logService.deleteAll();
        return createResponse(Constant.SUCCESS, "成功", null);
    }
}
