package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.model.Sample;
import com.chen.brand.model.User;
import com.chen.brand.service.SampleService;
import com.chen.brand.service.TableDataService;
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
@RequestMapping("/api/data")
@Api( value = "api -- TableDataController", description = "数据查询")
public class TableDataController extends BaseController{

    @Autowired
    private TableDataService dataService;

    @Autowired
    private SampleService sampleService;

    @ApiOperation(value = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableId", value = "报表id", dataType = "Long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "areaCode", value = "区县Code", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "planRound", value = "期次", dataType = "Date", paramType = "query")
    })
    @GetMapping("")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(defaultValue = "1") Long tableId,
                                       @RequestParam(required = false) String areaCode,
                                       @RequestParam(required = false) Date planRound){
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
        return createResponse(Constant.SUCCESS, "成功", dataService.findAll(tableId, areaCode, sampleId, planRound));
    }
}
