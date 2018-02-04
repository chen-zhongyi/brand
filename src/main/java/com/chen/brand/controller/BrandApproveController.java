package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.http.request.BrandApprove.BrandApproveRequest;
import com.chen.brand.model.*;
import com.chen.brand.service.BrandApproveService;
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
@RequestMapping( value = "/api/brandBest")
@Api( value = "api -- BrandApproveController", description = "名品管理接口")
public class BrandApproveController extends BaseController{

    @Autowired
    private BrandApproveService brandApproveService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SampleService sampleService;

    @ApiOperation(value = "创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "BrandApproveRequest", paramType = "body")
    })
    @PostMapping(value = "")
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest,
                                      @RequestBody @Valid BrandApproveRequest request,
                                      @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(brandApproveService.findByBrandIdAndYear(request.getBrandId(), request.getYear().trim()) != null){
            return createResponse(Constant.FAIL, "该品牌当前年份已经是名牌", null);
        }
        Brand brand = brandService.findOne(request.getBrandId());
        BrandApprove brandApprove = new BrandApprove();
        brandApprove.setBrandId(brand.getId());
        brandApprove.setUserId(brand.getUserId());
        brandApprove.setYear(request.getYear().trim());
        brandApproveService.insert(brandApprove);
        return createResponse(Constant.SUCCESS, "成功", brandApproveService.findOne(brandApprove.getId()));
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "BrandApproveRequest", paramType = "body")
    })
    @PutMapping(value = "/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid BrandApproveRequest request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if (brandApproveService.findOne(id) == null) {
            return createResponse(Constant.FAIL, "名品id不存在", null);
        }
        BrandApprove brandApprove = new BrandApprove();
        brandApprove.setId(id);
        brandApprove.setYear(request.getYear());
        brandApproveService.update(brandApprove);
        return createResponse(Constant.SUCCESS, "成功", brandApproveService.findOne(id));
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        if (brandApproveService.findOne(id) == null) {
            return createResponse(Constant.FAIL, "名品id不存在", null);
        }
        brandApproveService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation(value = "根据id获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id) {
        if (brandApproveService.findOne(id) == null) {
            return createResponse(Constant.FAIL, "名品id不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", brandApproveService.findOne(id));
    }

    @ApiOperation(value = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区县Code", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sampleName", value = "企业名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "brandName", value = "品牌名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "brandId", value = "品牌id", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页，分页大小", dataType = "int", paramType = "query", defaultValue = "20")
    })
    @GetMapping(value = "")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(required = false) String areaCode,
                                       @RequestParam(required = false) String sampleName,
                                       @RequestParam(required = false) String brandName,
                                       @RequestParam(required = false) Long brandId,
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

        return createResponse(Constant.SUCCESS, "成功", brandApproveService.findAll(sampleName, brandName, areaCode, sampleId, brandId, pageNumber, pageSize));
    }
}
