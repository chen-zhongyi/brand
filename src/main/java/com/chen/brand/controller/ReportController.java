package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.model.Sample;
import com.chen.brand.model.User;
import com.chen.brand.service.ReportService;
import com.chen.brand.service.SampleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.Map;

@RestController
@RequestMapping( value = "/api/reports")
@Api( value = "api -- reportController", description = "所有要填报表管理接口")
public class ReportController extends BaseController{

    @Autowired
    private ReportService reportService;

    @Autowired
    private SampleService sampleService;

    @ApiOperation(value = "获取所有要填的报表列表")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "areaCode", value = "区县代码，不必须", dataType = "String", paramType = "query"),
            @ApiImplicitParam( name = "sampleName", value = "企业名称，不必须", dataType = "String", paramType = "query"),
            @ApiImplicitParam( name = "planRound", value = "期次，不必须", dataType = "Date", paramType = "query"),
            @ApiImplicitParam( name = "tableId", value = "报表id，不必须", dataType = "Long", paramType = "query"),
            @ApiImplicitParam( name = "status", value = "状态,不必须,-1:未填报", dataType = "Long", paramType = "query"),
            @ApiImplicitParam( name = "pageNumber", value = "分页，第几页，不必须, 默认1", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam( name = "pageSize", value = "分页，分页大小，不必须，默认20", dataType = "int", paramType = "query", defaultValue = "20"),
    })
    @GetMapping( value = "")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(required = false) String areaCode,
                                       @RequestParam(required = false) String sampleName,
                                       @RequestParam(required = false) Date planRound,
                                       @RequestParam(required = false) Long tableId,
                                       @RequestParam(required = false) Long status,
                                       @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                       @RequestParam(required = false, defaultValue = "20") int pageSize){
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);Long sampleId = null;
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
        return createResponse(Constant.SUCCESS, "成功", reportService.findAll(areaCode, sampleId, sampleName, planRound, tableId, status, pageNumber, pageSize));
    }

}
