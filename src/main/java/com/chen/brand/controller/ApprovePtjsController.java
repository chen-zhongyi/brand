package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.http.request.ApprovePtjs.PtjsRequest;
import com.chen.brand.http.request.ApprovePtjs.PtjsStatus;
import com.chen.brand.model.ApprovePtjs;
import com.chen.brand.model.User;
import com.chen.brand.service.ApprovePtjsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping( value = "/api/ptjss")
@Api( value = "api --- ApprovePtjsController", description = "平台建设")
public class ApprovePtjsController extends BaseController{

    @Autowired
    private ApprovePtjsService ptjsService;

    @ApiOperation(value = "创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PtjsRequest", paramType = "body")
    })
    @PostMapping(value = "")
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest,
                                      @RequestBody @Valid PtjsRequest request,
                                      @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        ApprovePtjs ptjs = new ApprovePtjs();
        ptjs.setMc(request.getMc());
        ptjs.setLx(request.getLx());
        ptjs.setDw(request.getDw());
        ptjs.setCl(request.getCl());
        ptjs.setSj(request.getSj());
        ptjs.setStatus(1L);
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        ptjs.setUserId(user.getId());
        Long id = ptjsService.insert(ptjs);
        return createResponse(Constant.SUCCESS, "成功", ptjsService.findOne(id));
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PtjsRequest", paramType = "body")
    })
    @PutMapping(value = "/{id}")
    public Map<String, Object> update(@PathVariable Long id,
                                      @RequestBody @Valid PtjsRequest request,
                                      @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (ptjsService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ApprovePtjs ptjs = new ApprovePtjs();
        ptjs.setId(id);
        ptjs.setMc(request.getMc());
        ptjs.setLx(request.getLx());
        ptjs.setDw(request.getDw());
        ptjs.setCl(request.getCl());
        ptjs.setSj(request.getSj());
        ptjsService.update(ptjs);
        return createResponse(Constant.SUCCESS, "成功", ptjsService.findOne(id));
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        if (ptjsService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ptjsService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation(value = "根据id获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id) {
        if (ptjsService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", ptjsService.findOne(id));
    }

    @ApiOperation(value = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区县Code", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qymc", value = "企业名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页，分页大小", dataType = "int", paramType = "query", defaultValue = "20")
    })
    @GetMapping(value = "")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(value = "areaCode", required = false) String areaCode,
                                       @RequestParam(value = "qymc", required = false) String sampleName,
                                       @RequestParam(value = "status", required = false) Long status,
                                       @RequestParam("pageNumber") int pageNumber,
                                       @RequestParam("pageSize") int pageSize) {
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        Long userId = null;
        Long type = user.getType();
        if(type == Constant.QX_ADMIN){
            areaCode = user.getAreaCode();
        }else if(type == Constant.USER){
            userId = user.getId();
        }
        return createResponse(Constant.SUCCESS, "成功", ptjsService.findAll(areaCode, sampleName, status, userId, pageNumber, pageSize));
    }

    @ApiOperation(value = "审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PtjsStatus", paramType = "body")
    })
    @PutMapping(value = "/approve/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id,
                                            @RequestBody @Valid PtjsStatus request,
                                            @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (ptjsService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ApprovePtjs ptjs = new ApprovePtjs();
        ptjs.setId(id);
        ptjs.setStatus(request.getStatus());
        if(request.getStatus() >= 2 && request.getStatus() <= 3){
            ptjs.setFirstComment(request.getComment());
        }else if(request.getStatus() >= 4 && request.getStatus() <= 5){
            ptjs.setFinalComment(request.getComment());
        }
        ptjsService.update(ptjs);
        return createResponse(Constant.SUCCESS, "成功", ptjsService.findOne(id));
    }
}
