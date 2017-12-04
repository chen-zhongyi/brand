package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.http.request.ApproveKjjl.KjjlRequest;
import com.chen.brand.http.request.ApproveKjjl.KjjlStatus;
import com.chen.brand.model.ApproveKjjl;
import com.chen.brand.model.User;
import com.chen.brand.service.ApproveKjjlService;
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
@RequestMapping( value = "/api/kjjls")
@Api( value = "api --- ApproveKjjlController", description = "科技奖励")
public class ApproveKjjlController extends BaseController{

    @Autowired
    private ApproveKjjlService kjjlService;

    @ApiOperation(value = "创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "KjjlRequest", paramType = "body")
    })
    @PostMapping(value = "")
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest, @RequestBody @Valid KjjlRequest request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        ApproveKjjl kjjl = new ApproveKjjl();
        kjjl.setCgmc(request.getCgmc());
        kjjl.setJxmc(request.getJxmc());
        kjjl.setDj(request.getDj());
        kjjl.setDw(request.getDw());
        kjjl.setSj(request.getSj());
        kjjl.setCl(request.getCl());
        kjjl.setStatus(ApproveStatus.NotApprove.getStatus());
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        kjjl.setUserId(user.getId());
        Long id = kjjlService.insert(kjjl);
        return createResponse(Constant.SUCCESS, "成功", kjjlService.findOne(id));
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "KjjlRequest", paramType = "body")
    })
    @PutMapping(value = "/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid KjjlRequest request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (kjjlService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ApproveKjjl kjjl = new ApproveKjjl();
        kjjl.setId(id);
        kjjl.setCgmc(request.getCgmc());
        kjjl.setJxmc(request.getJxmc());
        kjjl.setDj(request.getDj());
        kjjl.setDw(request.getDw());
        kjjl.setSj(request.getSj());
        kjjl.setCl(request.getCl());
        kjjlService.update(kjjl);
        return createResponse(Constant.SUCCESS, "成功", kjjlService.findOne(id));
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        if (kjjlService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        kjjlService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation(value = "根据id获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id) {
        if (kjjlService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", kjjlService.findOne(id));
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
        return createResponse(Constant.SUCCESS, "成功", kjjlService.findAll(areaCode, sampleName, status, userId, pageNumber, pageSize));
    }

    @ApiOperation(value = "审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "KjjlStatus", paramType = "body")
    })
    @PutMapping(value = "/approve/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id, @RequestBody @Valid KjjlStatus request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (kjjlService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ApproveKjjl kjjl = new ApproveKjjl();
        kjjl.setId(id);
        kjjl.setStatus(request.getStatus());
        kjjl.setDj(request.getDj());
        if(request.getStatus() == ApproveStatus.FirstApproveNotPass.getStatus() || request.getStatus() == ApproveStatus.FirstApprovePass.getStatus()){
            kjjl.setFirstComment(request.getComment());
        }else if(request.getStatus() == ApproveStatus.FinalApproveNotPass.getStatus() || request.getStatus() == ApproveStatus.FinalApprovePass.getStatus()){
            kjjl.setFinalComment(request.getComment());
        }
        kjjlService.update(kjjl);
        return createResponse(Constant.SUCCESS, "成功", kjjlService.findOne(id));
    }
}
