package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.http.request.ApproveZmcl.ZmclRequest;
import com.chen.brand.http.request.ApproveZmcl.ZmclStatus;
import com.chen.brand.model.ApproveZmcl;
import com.chen.brand.model.User;
import com.chen.brand.service.ApproveZmclService;
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
@RequestMapping( value = "/api/zmcls")
@Api( value = "api --- ApproveZmclController", description = "证明材料上传")
public class ApproveZmclController extends BaseController{

    @Autowired
    private ApproveZmclService zmclService;

    @ApiOperation(value = "创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "ZmclRequest", paramType = "body")
    })
    @PostMapping(value = "")
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest,
                                      @RequestBody @Valid ZmclRequest request,
                                      @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        ApproveZmcl zmcl = new ApproveZmcl();
        zmcl.setFzb(request.getFzb());
        zmcl.setXse(request.getXse());
        zmcl.setLrb(request.getLrb());
        zmcl.setPm(request.getPm());
        zmcl.setYear(request.getYear());
        zmcl.setStatus(1L);
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        zmcl.setUserId(user.getId());
        Long id = zmclService.insert(zmcl);
        return createResponse(Constant.SUCCESS, "成功", zmclService.findOne(id));
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "ZmclRequest", paramType = "body")
    })
    @PutMapping(value = "/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid ZmclRequest request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (zmclService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ApproveZmcl zmcl = new ApproveZmcl();
        zmcl.setId(id);
        zmcl.setFzb(request.getFzb());
        zmcl.setXse(request.getXse());
        zmcl.setLrb(request.getLrb());
        zmcl.setPm(request.getPm());
        zmcl.setYear(request.getYear());
        zmclService.update(zmcl);
        return createResponse(Constant.SUCCESS, "成功", zmclService.findOne(id));
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        if (zmclService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        zmclService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation(value = "根据id获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id) {
        if (zmclService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", zmclService.findOne(id));
    }

    @ApiOperation(value = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区县Code", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qymc", value = "企业名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页，分页大小", dataType = "int", paramType = "query", defaultValue = "20")
    })
    @GetMapping(value = "")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(value = "areaCode", required = false) String areaCode,
                                       @RequestParam(value = "qymc", required = false) String sampleName,
                                       @RequestParam(value = "status", required = false) Long status,
                                       @RequestParam(value = "year", required = false) String year,
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
        return createResponse(Constant.SUCCESS, "成功", zmclService.findAll(areaCode, sampleName, status, userId, year, pageNumber, pageSize));
    }

    @ApiOperation(value = "审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "ZmclStatus", paramType = "body")
    })
    @PutMapping(value = "/approve/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id, @RequestBody @Valid ZmclStatus request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (zmclService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ApproveZmcl zmcl = new ApproveZmcl();
        zmcl.setId(id);
        zmcl.setStatus(request.getStatus());
        if(request.getStatus() >= 2 && request.getStatus() <= 3){
            zmcl.setFirstComment(request.getComment());
        }else if(request.getStatus() >= 4 && request.getStatus() <= 5){
            zmcl.setFinalComment(request.getComment());
        }
        zmclService.update(zmcl);
        return createResponse(Constant.SUCCESS, "成功", zmclService.findOne(id));
    }
}
