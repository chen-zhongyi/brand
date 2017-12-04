package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.http.request.brand.BrandApprove;
import com.chen.brand.http.request.brand.BrandInsert;
import com.chen.brand.model.Brand;
import com.chen.brand.model.Sample;
import com.chen.brand.model.User;
import com.chen.brand.service.BrandService;
import com.chen.brand.service.SampleService;
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
@RequestMapping( value = "/api/brands")
@Api( value = "api -- BrandController", description = "品牌管理接口")
public class BrandController extends BaseController{

    @Autowired
    private BrandService brandService;

    @Autowired
    private SampleService sampleService;

    @ApiOperation(value = "创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "BrandInsert", paramType = "body")
    })
    @PostMapping(value = "")
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest,
                                      @RequestBody @Valid BrandInsert request,
                                      @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }

        Brand brand = new Brand();
        brand.setPpmc(request.getPpmc());
        brand.setPpsb(request.getPpsb());
        brand.setPplx(request.getPplx());
        brand.setSplx(request.getSplx());
        brand.setSp(request.getSp());
        brand.setZcrq(request.getZcrq());
        brand.setZcd(request.getZcd());
        brand.setZcdGw(request.getZcdGw());
        brand.setZs(request.getZs());
        brand.setStatus(1L);
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        Long userId = user.getId();
        Sample sample = sampleService.findByUserId(userId);
        brand.setSampleId(sample.getId());
        brand.setUserId(user.getId());
        Long id = brandService.insert(brand);
        return createResponse(Constant.SUCCESS, "成功", brandService.findOne(id));
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "BrandInsert", paramType = "body")
    })
    @PutMapping(value = "/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid BrandInsert request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if (brandService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "品牌id不存在", null);
        }
        Brand brand = new Brand();
        brand.setId(id);
        brand.setPpmc(request.getPpmc());
        brand.setPpsb(request.getPpsb());
        brand.setPplx(request.getPplx());
        brand.setSplx(request.getSplx());
        brand.setSp(request.getSp());
        brand.setZcrq(request.getZcrq());
        brand.setZcd(request.getZcd());
        brand.setZcdGw(request.getZcdGw());
        brand.setZs(request.getZs());
        brandService.update(brand);
        return createResponse(Constant.SUCCESS, "成功", brandService.findOne(id));
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        if (brandService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "品牌id不存在", null);
        }
        brandService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation(value = "根据id获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id) {
        if (brandService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "品牌id不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", brandService.findOne(id));
    }

    @ApiOperation(value = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区县Code", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ppmc", value = "品牌名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页，分页大小", dataType = "int", paramType = "query", defaultValue = "20")
    })
    @GetMapping(value = "")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(required = false) String areaCode,
                                       @RequestParam(required = false) String ppmc,
                                       @RequestParam(required = false) Long status,
                                       @RequestParam(defaultValue = "1")int pageNumber,
                                       @RequestParam(defaultValue = "20") int pageSize) {
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        Long sampleId = null;
        Long type = user.getType();
        if(type == Constant.QX_ADMIN){
            areaCode = user.getAreaCode();
        }else if(type == Constant.USER){
            Long userId = user.getId();
            if(sampleService.findByUserId(userId) == null)
                sampleId = -1L;
            else {
                Sample sample = sampleService.findByUserId(userId);
                sampleId = sample.getId();
            }
        }

        return createResponse(Constant.SUCCESS, "成功", brandService.findAll(areaCode, sampleId, ppmc, status, pageNumber, pageSize));
    }

    @ApiOperation(value = "审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "审核状态0-未审核, 1-已审核", dataType = "BrandApprove", paramType = "body")
    })
    @PutMapping(value = "/approve/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id, @RequestBody @Valid BrandApprove request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if (brandService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "品牌id不存在", null);
        }
        brandService.approve(id, request.getStatus());
        return createResponse(Constant.SUCCESS, "成功", brandService.findOne(id));
    }
}
