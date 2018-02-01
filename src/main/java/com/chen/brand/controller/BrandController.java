package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.http.request.brand.BrandApprove;
import com.chen.brand.http.request.brand.BrandInsert;
import com.chen.brand.model.Area;
import com.chen.brand.model.Brand;
import com.chen.brand.model.Sample;
import com.chen.brand.model.User;
import com.chen.brand.service.AreaService;
import com.chen.brand.service.BrandService;
import com.chen.brand.service.SampleService;
import com.chen.brand.util.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value = "/api/brands")
@Api( value = "api -- BrandController", description = "品牌管理接口")
public class BrandController extends BaseController{

    @Autowired
    private BrandService brandService;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private AreaService areaService;

    @Value("${web.upload-path}")
    private String UPLOAD_PATH;

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

    @ApiOperation(value = "导出列表到excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区县Code", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ppmc", value = "品牌名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "Long", paramType = "query")
    })
    @GetMapping(value = "/excel")
    public Map<String, Object> excel(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(required = false) String areaCode,
                                       @RequestParam(required = false) String ppmc,
                                       @RequestParam(required = false) Long status) {
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
        List<List<String>> data = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(new String[]{"序号", "企业名称", "所在区县", "申报品牌名称", "品牌商标", "状态", "是否是名品", "是否曾今是名品", "最近评定名品年份", "品牌类型", "商品类型", "商品", "首次注册/签发", "首次注册地"}));
        data.add(temp);
        List<Object> brands = (List<Object>) brandService.findAll(areaCode, sampleId, ppmc, status, 1, Integer.MAX_VALUE).get("list");
        int i = 1;
        for(Object b : brands){
            Brand brand = (Brand) b;
            temp = new ArrayList<>();
            temp.add(i + "");i++;
            temp.add(isNull(brand.getSampleName()));
            Area area = areaService.findOne(brand.getAreaCode());
            temp.add(area == null ? "" : area.getName());
            temp.add(isNull(brand.getPpmc()));
            temp.add(isNull(brand.getPpsb()).equals("") ? "" : "http://114.55.103.228:8083/" + isNull(brand.getPpsb()));
            ApproveStatus s = ApproveStatus.convert(brand.getStatus());
            temp.add(s == null ? "" : s.getDescription());
            temp.add(brand.getBest() == true ? "是" : "否");
            temp.add(brand.getEverBest() == true ? "是" : "否");
            temp.add(isNull(brand.getYear()));
            temp.add(isNull(brand.getPplx()));
            temp.add(isNull(brand.getSplx()));
            temp.add(isNull(brand.getSp()));
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            temp.add(isNull(sf.format(brand.getZcrq())));
            temp.add(isNull(brand.getZcd()));
            data.add(temp);
        }
        String path = "excels" + File.separator + user.getId() + System.currentTimeMillis() + ".xls";
        File file = new File(UPLOAD_PATH + path);
        ExcelUtils.export(data, file);
        return createResponse(Constant.SUCCESS, "成功", path);
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
