package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.http.request.sample.SampleApprove;
import com.chen.brand.http.request.sample.SampleInsert;
import com.chen.brand.http.request.sample.SampleUpdate;
import com.chen.brand.model.Area;
import com.chen.brand.model.Sample;
import com.chen.brand.model.User;
import com.chen.brand.service.AreaService;
import com.chen.brand.service.SampleService;
import com.chen.brand.service.UserService;
import com.chen.brand.util.ExcelUtils;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value = "/api/samples")
@Api( value = "sampleController", description = "样本企业管理接口")
public class SampleController extends BaseController{

    @Autowired
    private SampleService sampleService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private UserService userService;

    @Value("${web.upload-path}")
    private String UPLOAD_PATH;

    @ApiOperation(value = "创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "SampleInsert", paramType = "body")
    })
    @PostMapping(value = "")
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest, @RequestBody @Valid SampleInsert request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(areaService.isExist(request.getSsqx()) == false){
            return createResponse(Constant.FAIL, "所属区县不存在", null);
        }
        System.out.println(new Gson().toJson(request));
        User user = new User();
        User temp = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        user.setId(temp.getId());
        user.setAreaCode(request.getSsqx());
        userService.update(user);

        Sample sample = new Sample();
        sample.setQymcCn(request.getQymcCn());
        sample.setQymcEn(request.getQymcEn());
        sample.setQylx(request.getQylx());
        sample.setQysyzxz(request.getQysyzxz());
        sample.setQyjj(request.getQyjj());
        sample.setZcdz(request.getZcdz());
        sample.setFrdb(request.getFrdb());
        sample.setLxr(request.getLxr());
        sample.setLxdh(request.getLxdh());
        sample.setLxsj(request.getLxsj());
        sample.setXydm(request.getXydm());
        sample.setDzsw(request.getDzsw());
        sample.setSsqx(request.getSsqx());
        sample.setWz(request.getWz());
        sample.setBgdz(request.getBgdz());
        sample.setYyzz(request.getYyzz());
        sample.setDjzs(request.getDjzs());
        sample.setZczb(request.getZczb());
        sample.setStatus(0L);
        sample.setUserId(temp.getId());
        Sample one = sampleService.findByUserId(temp.getId());
        Long id;
        if (one != null) {
            sample.setId(one.getId());
            id = one.getId();
            sampleService.update(sample, request.getBgzs());
        } else {
            id = sampleService.insert(sample, request.getBgzs());
        }
        return createResponse(Constant.SUCCESS, "成功", sampleService.findOne(id));
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "SampleUpdate", paramType = "body")
    })
    @PutMapping(value = "/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid SampleUpdate request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if (sampleService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "样本企业不存在", null);
        }
        Sample sample = new Sample();
        sample.setId(id);
        sample.setQymcCn(request.getQymcCn());
        sample.setQymcEn(request.getQymcEn());
        sample.setQylx(request.getQylx());
        sample.setQysyzxz(request.getQysyzxz());
        sample.setQyjj(request.getQyjj());
        sample.setZcdz(request.getZcdz());
        sample.setFrdb(request.getFrdb());
        sample.setLxr(request.getLxr());
        sample.setLxdh(request.getLxdh());
        sample.setLxsj(request.getLxsj());
        sample.setXydm(request.getXydm());
        sample.setDzsw(request.getDzsw());
        sample.setWz(request.getWz());
        sample.setBgdz(request.getBgdz());
        sample.setYyzz(request.getYyzz());
        sample.setDjzs(request.getDjzs());
        sample.setZczb(request.getZczb());
        sampleService.update(sample, request.getBgzs());
        return createResponse(Constant.SUCCESS, "成功", sampleService.findOne(id));
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        if (sampleService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "样本企业不存在", null);
        }
        sampleService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation(value = "根据id获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id) {
        if (sampleService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "样本企业不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", sampleService.findOne(id));
    }

    @ApiOperation(value = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xydm", value = "社会信用代码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qymc", value = "企业名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页，分页大小", dataType = "int", paramType = "query", defaultValue = "20")
    })
    @GetMapping(value = "")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(required = false) String areaCode,
                                       @RequestParam(required = false) String xydm,
                                       @RequestParam(required = false) String qymc,
                                       @RequestParam(defaultValue = "1")int pageNumber,
                                       @RequestParam(defaultValue = "20") int pageSize) {
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        Long userId = null;
        Long type = user.getType();
        if(type == Constant.QX_ADMIN){
            areaCode = user.getAreaCode();
        }else if(type == Constant.USER){
            userId = user.getId();
        }
        return createResponse(Constant.SUCCESS, "成功", sampleService.findAll(areaCode, userId, xydm, qymc, pageNumber, pageSize));
    }

    @ApiOperation(value = "企业信息导出到Excel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xydm", value = "社会信用代码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "qymc", value = "企业名称", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "/excel")
    public Map<String, Object> excelExport(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(required = false) String areaCode,
                                       @RequestParam(required = false) String xydm,
                                       @RequestParam(required = false) String qymc) {
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        Long userId = null;
        Long type = user.getType();
        if(type == Constant.QX_ADMIN){
            areaCode = user.getAreaCode();
        }else if(type == Constant.USER){
            userId = user.getId();
        }
        List<List<String>> data = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(new String[]{"序号", "中文名称", "英文名称", "法人代表", "统一社会信用代码", "企业类型", "注册资本(元)", "联系人", "联系电话", "所在区县"}));
        data.add(temp);
        List<Map<String, Object>> samples = (List<Map<String, Object>>) sampleService.findAll(areaCode, userId, xydm, qymc, 1, Integer.MAX_VALUE).get("list");
        int i = 1;
        for(Map<String, Object> sample : samples){
            Sample s = (Sample) sample.get("sample");
            temp = new ArrayList<>();
            temp.add(i + "");i++;
            temp.add(isNull(s.getQymcCn()));
            temp.add(isNull(s.getQymcEn()));
            temp.add(isNull(s.getFrdb()));
            temp.add(isNull(s.getXydm()));
            temp.add(isNull(s.getQylx()));
            temp.add(isNull(s.getZczb()));
            temp.add(isNull(s.getLxr()));
            temp.add(isNull(s.getLxdh()));
            Area area = areaService.findOne(s.getSsqx());
            temp.add(area == null ? "" : area.getName());
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
            @ApiImplicitParam(name = "request", value = "审核状态0-未审核, 1-已审核", dataType = "SampleApprove", paramType = "body")
    })
    @PutMapping(value = "/approve/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id, @RequestBody @Valid SampleApprove request, @ApiIgnore Errors errors) {
        if (errors.hasErrors()) {
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if (sampleService.isExist(id) == false) {
            return createResponse(Constant.FAIL, "样本企业不存在", null);
        }
        sampleService.approve(id, request.getStatus());
        return createResponse(Constant.SUCCESS, "成功", sampleService.findOne(id));
    }
}
