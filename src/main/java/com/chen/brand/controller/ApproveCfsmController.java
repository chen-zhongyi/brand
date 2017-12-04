package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.http.request.ApproveCfsm.CfsmRequest;
import com.chen.brand.http.request.ApproveCfsm.CfsmStatus;
import com.chen.brand.model.ApproveCfsm;
import com.chen.brand.model.User;
import com.chen.brand.service.ApproveCfsmService;
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
@RequestMapping( value = "/api/cfsms")
@Api( value = "api --- ApproveCfsmController", description = "部门处罚说明")
public class ApproveCfsmController extends BaseController{

    @Autowired
    private ApproveCfsmService cfsmService;

    @ApiOperation(value = "创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "CfsmRequest", paramType = "body")
    })
    @PostMapping(value = "")
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest,
                                      @RequestBody @Valid CfsmRequest request,
                                      @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        ApproveCfsm cfsm = new ApproveCfsm();
        cfsm.setSx(request.getSx());
        cfsm.setDw(request.getDw());
        cfsm.setSj(request.getSj());
        cfsm.setSm(request.getSm());
        cfsm.setStatus(ApproveStatus.NotApprove.getStatus());
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        cfsm.setUserId(user.getId());
        Long id = cfsmService.insert(cfsm);
        return createResponse(Constant.SUCCESS, "成功", cfsmService.findOne(id));
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "CfsmRequest", paramType = "body")
    })
    @PutMapping(value = "/{id}")
    public Map<String, Object> update(@PathVariable Long id,
                                      @RequestBody @Valid CfsmRequest request,
                                      @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (cfsmService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ApproveCfsm cfsm = new ApproveCfsm();
        cfsm.setId(id);
        cfsm.setSx(request.getSx());
        cfsm.setDw(request.getDw());
        cfsm.setSj(request.getSj());
        cfsm.setSm(request.getSm());
        cfsmService.update(cfsm);
        return createResponse(Constant.SUCCESS, "成功", cfsmService.findOne(id));
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        if (cfsmService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        cfsmService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation(value = "根据id获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id) {
        if (cfsmService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", cfsmService.findOne(id));
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
        return createResponse(Constant.SUCCESS, "成功", cfsmService.findAll(areaCode, sampleName, status, userId, pageNumber, pageSize));
    }

    @ApiOperation(value = "审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "CfsmStatus", paramType = "body")
    })
    @PutMapping(value = "/approve/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id, @RequestBody @Valid CfsmStatus request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (cfsmService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ApproveCfsm cfsm = new ApproveCfsm();
        cfsm.setId(id);
        cfsm.setStatus(request.getStatus());
        if(request.getStatus() == ApproveStatus.FirstApproveNotPass.getStatus() || request.getStatus() == ApproveStatus.FirstApprovePass.getStatus()){
            cfsm.setFirstComment(request.getComment());
        }else if(request.getStatus() == ApproveStatus.FinalApproveNotPass.getStatus() || request.getStatus() == ApproveStatus.FinalApprovePass.getStatus()){
            cfsm.setFinalComment(request.getComment());
        }
        cfsmService.update(cfsm);
        return createResponse(Constant.SUCCESS, "成功", cfsmService.findOne(id));
    }
}
