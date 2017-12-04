package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.http.request.ApprovePpcke.PpckeRequest;
import com.chen.brand.http.request.ApprovePpcke.PpckeStatus;
import com.chen.brand.model.ApprovePpcke;
import com.chen.brand.model.User;
import com.chen.brand.model.Year;
import com.chen.brand.service.ApprovePpckeService;
import com.chen.brand.service.BrandService;
import com.chen.brand.service.YearService;
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
@RequestMapping( value = "/api/ppckes")
@Api( value = "api --- ApprovePpckeController", description = "品牌申报出口额")
public class ApprovePpckeController extends BaseController{

    @Autowired
    private ApprovePpckeService ppckeService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private YearService yearService;

    @ApiOperation(value = "创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PpckeRequest", paramType = "body")
    })
    @PostMapping(value = "")
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest,
                                      @RequestBody @Valid PpckeRequest request,
                                      @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if(brandService.isExist(request.getBrandId()) == false){
            return createResponse(Constant.FAIL, "品牌ID不存在", null);
        }
        ApprovePpcke ppcke = new ApprovePpcke();
        ppcke.setCke(request.getCke());
        ppcke.setOne(request.getOne());
        ppcke.setTwo(request.getTwo());
        ppcke.setThree(request.getThree());
        ppcke.setFour(request.getFour());
        ppcke.setFive(request.getFive());
        ppcke.setStatus(ApproveStatus.NotApprove.getStatus());
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        ppcke.setUserId(user.getId());
        ppcke.setYear(request.getYear());
        ppcke.setBrandId(request.getBrandId());
        Long id = ppckeService.insert(ppcke);
        return createResponse(Constant.SUCCESS, "成功", ppckeService.findOne(id));
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PpckeRequest", paramType = "body")
    })
    @PutMapping(value = "/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid PpckeRequest request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (ppckeService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ApprovePpcke ppcke = new ApprovePpcke();
        ppcke.setId(id);
        ppcke.setCke(request.getCke());
        ppcke.setOne(request.getOne());
        ppcke.setTwo(request.getTwo());
        ppcke.setThree(request.getThree());
        ppcke.setFour(request.getFour());
        ppcke.setFive(request.getFive());
        ppckeService.update(ppcke);
        return createResponse(Constant.SUCCESS, "成功", ppckeService.findOne(id));
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        if (ppckeService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ppckeService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation(value = "根据id获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id) {
        if (ppckeService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", ppckeService.findOne(id));
    }

    @ApiOperation(value = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区县Code", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qymc", value = "企业名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "year", value = "年份", dataType = "String", paramType = "query", defaultValue = "2015"),
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页，分页大小", dataType = "int", paramType = "query", defaultValue = "20")
    })
    @GetMapping(value = "")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(value = "areaCode", required = false) String areaCode,
                                       @RequestParam(value = "qymc", required = false) String sampleName,
                                       @RequestParam(value = "status", required = false) Long status,
                                       @RequestParam(value = "year", defaultValue = "2015") String year,
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
        for(String y : year.split(",")){
            if(yearService.isExist(y) == false){
                Year temp = new Year();
                temp.setYear(y);
                yearService.insert(temp);
            }
        }
        return createResponse(Constant.SUCCESS, "成功", ppckeService.findAll(areaCode, sampleName, status, userId, year, pageNumber, pageSize));
    }

    @ApiOperation(value = "审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PpckeStatus", paramType = "body")
    })
    @PutMapping(value = "/approve/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id, @RequestBody @Valid PpckeStatus request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", null);
        }
        if (ppckeService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        ApprovePpcke ppcke = new ApprovePpcke();
        ppcke.setId(id);
        ppcke.setStatus(request.getStatus());
        if(request.getStatus() == ApproveStatus.FirstApproveNotPass.getStatus() || request.getStatus() == ApproveStatus.FirstApprovePass.getStatus()){
            ppcke.setFirstComment(request.getComment());
        }else if(request.getStatus() == ApproveStatus.FinalApproveNotPass.getStatus() || request.getStatus() == ApproveStatus.FinalApprovePass.getStatus()){
            ppcke.setFinalComment(request.getComment());
        }
        ppckeService.update(ppcke);
        return createResponse(Constant.SUCCESS, "成功", ppckeService.findOne(id));
    }

    @ApiOperation(value = "企业获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份，如2015,2016", dataType = "String", paramType = "query", defaultValue = "2015")
    })
    @GetMapping("/findByYear/{id}")
    public Map<String, Object> findByYear(@ApiIgnore @CookieValue("_chen_jwt") String jwt,
                                          @RequestParam(required = false, defaultValue = "2015") String year){
        Long type = getUserType(jwt);
        Long userId = null;
        if(type == Constant.USER){
            userId = getUserId(jwt);
        }else {
            return createResponse(Constant.FAIL, "企业获取品牌申报出口额列表", null);
        }
        for(String y : year.split(",")){
            if(yearService.isExist(y) == false){
                Year temp = new Year();
                temp.setYear(y);
                yearService.insert(temp);
            }
        }
        return createResponse(Constant.SUCCESS, "成功", ppckeService.findByYear(userId, year));
    }
}
