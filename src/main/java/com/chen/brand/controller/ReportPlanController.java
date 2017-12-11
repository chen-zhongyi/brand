package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.http.request.Plan.PlanRequest;
import com.chen.brand.model.ReportPlan;
import com.chen.brand.model.User;
import com.chen.brand.service.ReportPlanService;
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
import java.sql.Timestamp;
import java.util.Map;

@RestController
@RequestMapping( value = "/api/plans")
@Api( value = "api -- reportPlanController", description = "报表计划接口")
public class ReportPlanController extends BaseController {

    @Autowired
    private ReportPlanService planService;

    @ApiOperation(value = "创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PlanRequest", paramType = "body")
    })
    @PostMapping(value = "")
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest,
                                      @RequestBody @Valid PlanRequest request,
                                      @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        ReportPlan plan = new ReportPlan();
        plan.setName(request.getName());
        plan.setStartAt(request.getStartAt());
        plan.setEndAt(request.getEndAt());
        plan.setGroupId(request.getGroupId());
        plan.setRound(request.getRound());
        plan.setStatus(true);
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        plan.setCreateBy(user.getId());
        plan.setCreateAt(new Timestamp(System.currentTimeMillis()));
        Long id = planService.insert(plan);
        return createResponse(Constant.SUCCESS, "成功", planService.findOne(id));
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PlanRequest", paramType = "body")
    })
    @PutMapping(value = "/{id}")
    public Map<String, Object> update(@ApiIgnore HttpServletRequest httpRequest,
                                      @PathVariable Long id,
                                      @RequestBody @Valid PlanRequest request,
                                      @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (planService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "计划不存在", null);
        }
        ReportPlan plan = new ReportPlan();
        plan.setId(id);
        plan.setName(request.getName());
        plan.setStartAt(request.getStartAt());
        plan.setEndAt(request.getEndAt());
        plan.setGroupId(request.getGroupId());
        plan.setRound(request.getRound());
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        plan.setModifyBy(user.getId());
        plan.setModifyAt(new Timestamp(System.currentTimeMillis()));
        planService.update(plan);
        return createResponse(Constant.SUCCESS, "成功", planService.findOne(id));
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        if (planService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "计划不存在", null);
        }
        planService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation(value = "根据id获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id) {
        if (planService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "计划不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", planService.findOne(id));
    }

    @ApiOperation(value = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "int", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页，分页大小", dataType = "int", defaultValue = "20", paramType = "query")
    })
    @GetMapping(value = "")
    public Map<String, Object> findAll(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                       @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        return createResponse(Constant.SUCCESS, "成功", planService.findAll(pageNumber, pageSize));
    }

    @ApiOperation(value = "改变状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @PutMapping(value = "/changeStatus/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id) {
        if (planService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "计划不存在", null);
        }
        ReportPlan plan = planService.findOne(id);
        planService.changeStatus(id, plan.getStatus() == true ? false : true);
        return createResponse(Constant.SUCCESS, "成功", planService.findOne(id));
    }
}