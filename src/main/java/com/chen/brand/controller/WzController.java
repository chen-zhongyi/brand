package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.model.Wz;
import com.chen.brand.service.WzService;
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
@RequestMapping( value = "/api/wzs")
@Api( value = "api -- WzController", description = "跨境电子商务活动依托的平台管理")
public class WzController extends BaseController {

    @Autowired
    private WzService wzService;

    @ApiOperation(value = "创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "Wz", paramType = "body")
    })
    @PostMapping(value = "")
    public Map<String, Object> insert(@RequestBody @Valid Wz request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        request.setStatus(true);
        Long id = wzService.insert(request);
        return createResponse(Constant.SUCCESS, "成功", wzService.findOne(id));
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "Wz", paramType = "body")
    })
    @PutMapping(value = "/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid Wz request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (wzService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        request.setId(id);
        request.setStatus(null);
        wzService.update(request);
        return createResponse(Constant.SUCCESS, "成功", wzService.findOne(id));
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        if (wzService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        wzService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation(value = "根据id获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id) {
        if (wzService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", wzService.findOne(id));
    }

    @ApiOperation(value = "获取列表")
    @GetMapping(value = "")
    public Map<String, Object> findAll() {
        return createResponse(Constant.SUCCESS, "成功", wzService.findAll());
    }

    @ApiOperation(value = "改变状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @PutMapping(value = "/changeStatus/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id) {
        if (wzService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "计划不存在", null);
        }
        Wz wz = wzService.findOne(id);
        wzService.changeStatus(id, wz.getStatus() == true ? false : true);
        return createResponse(Constant.SUCCESS, "成功", wzService.findOne(id));
    }
}