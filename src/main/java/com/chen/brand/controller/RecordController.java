package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.http.request.record.Approve;
import com.chen.brand.http.request.record.RecordRequest;
import com.chen.brand.model.*;
import com.chen.brand.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value = "/api/records")
@Api( value = "api -- RecordController", description = "报表填报管理")
public class RecordController extends BaseController{

    @Autowired
    private RecordService recordService;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private ReportPlanService planService;

    @Autowired
    private TableService tableService;

    @Autowired
    private BrandService brandService;

    @ApiOperation( value = "报表填报")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "request", value = "请求体,表1传对象base，表2传对象jygn,表3传对象xse，表4传对象qkdc", dataType = "RecordRequest", paramType = "Body")
    })
    @PostMapping( "" )
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest,
                                      @RequestBody RecordRequest request,
                                      @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(sampleService.isExist(request.getSampleId()) == false){
            return createResponse(Constant.FAIL, "企业不存在", null);
        }
        if(tableService.isExist(request.getTableId()) == false){
            return createResponse(Constant.FAIL, "报表不存在", null);
        }
        if(planService.isExist(request.getPlanId()) == false){
            return createResponse(Constant.FAIL, "报表计划不存在", null);
        }
        Record record = new Record();
        record.setTableId(request.getTableId());
        record.setPlanId(request.getPlanId());
        record.setSampleId(request.getSampleId());
        record.setStatus(ApproveStatus.NotApprove.getStatus());
        record.setCreateAt(new Timestamp(System.currentTimeMillis()));
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        record.setCreateBy(user.getId());
        Map<Long, TableXse> xses = request.getXses();
        if(xses != null) {
            String err = "";
            for(Long xseId : xses.keySet()){
                if(brandService.isExist(xseId) == false)    err += xseId + ", ";
            }
            if(! err.equals("")){
                return createResponse(Constant.FAIL, "品牌id(" + err + ")不存在", null);
            }
        }
        Long id = recordService.insert(record, request.getBase(), request.getJygm(), request.getXses(), request.getQkdc());
        return createResponse(Constant.SUCCESS, "成功", recordService.findOne(id));
    }

    @ApiOperation( value = "报表修改")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "ID", dataType = "Long", paramType = "path"),
            @ApiImplicitParam( name = "request", value = "请求体,表1传对象base，表2传对象jygn,表3传对象xse，表4传对象qkdc", dataType = "RecordRequest", paramType = "Body")
    })
    @PutMapping( "/{id}")
    public Map<String, Object> update(@ApiIgnore HttpServletRequest httpRequest,
                                      @PathVariable Long id,
                                      @RequestBody RecordRequest request,
                                      @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(recordService.isExist(id) == false){
            return createResponse(Constant.FAIL, "记录不存在", null);
        }
        Map<String, Object> r = recordService.findOne(id);
        System.out.println("r --> " + r);
        Record record = new Record();
        record.setModifyAt(new Timestamp(System.currentTimeMillis()));
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        record.setModifyBy(user.getId());
        record.setId(id);
        Record temp = (Record)r.get("record");
        record.setTableId(temp.getTableId());
        record.setTableReportId(temp.getTableReportId());
        List<TableBase> bases = request.getBase();
        if(bases != null) {
            for(TableBase base : bases) {
                base.setRecordId(id);
            }
        }
        TableJygm jygm = request.getJygm();
        if(jygm != null) jygm.setRecordId(id);
        Map<Long, TableXse> xses = request.getXses();
        if(xses != null) {
            String err = null;
            for(Long xseId : xses.keySet()){
                if(brandService.isExist(xseId) == false)    err += xseId + ", ";
                TableXse xse = xses.get(xseId);
                if(xse != null) {
                    xse.setRecordId(id);
                    xse.setBrandId(xseId);
                }
            }
            if(err != null){
                return createResponse(Constant.FAIL, err + "品牌id不存在", null);
            }
        }
        TableQkdc qkdc = request.getQkdc();
        if(qkdc != null) qkdc.setRecordId(id);
        recordService.update(record, bases, jygm, xses, qkdc);
        return createResponse(Constant.SUCCESS, "成功", recordService.findOne(id));
    }

    @ApiOperation(value = "根据ID获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "ID", dataType = "Long", paramType = "path")
    })
    @GetMapping( "/{id}" )
    public Map<String, Object> findOne(@PathVariable("id") Long id){
        if(recordService.isExist(id) == false){
            return createResponse(Constant.FAIL, "记录不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", recordService.findOne(id));
    }

    @ApiOperation(value = "获取列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "areaCode", value = "区县Code", dataType = "String", paramType = "query"),
            @ApiImplicitParam( name = "sampleName", value = "企业名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam( name = "planRound", value = "期次", dataType = "Date", paramType = "query"),
            @ApiImplicitParam( name = "tableId", value = "报表id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam( name = "pageNumber", value = "分页，第几页，默认值1", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam( name = "pageSize", value = "分页，分页大小，默认值20", dataType = "int", paramType = "query", defaultValue = "20"),
    })
    @GetMapping("")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(required = false) String areaCode,
                                       @RequestParam(required = false) String sampleName,
                                       @RequestParam(required = false) Date planRound,
                                       @RequestParam(required = false) Long tableId,
                                       @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                       @RequestParam(required = false, defaultValue = "20") int pageSize){
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
        return createResponse(Constant.SUCCESS, "成功", recordService.findAll(areaCode, sampleId, sampleName, planRound, tableId, pageNumber, pageSize));
    }

    @ApiOperation( value = "报表审核")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "ID", dataType = "Long", paramType = "path"),
            @ApiImplicitParam( name = "request", value = "请求体, status:1-未审核 2-初审未通过  3-初审通过 4-终审未, comment:审核意见", dataType = "Approve", paramType = "Body")
    })
    @PutMapping("/approve/{id}")
    public Map<String, Object> approve(@ApiIgnore HttpServletRequest httpRequest, @PathVariable Long id, @RequestBody Approve request, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(recordService.isExist(id) == false){
            return createResponse(Constant.FAIL, "记录不存在", null);
        }
        Record record = new Record();
        record.setId(id);
        record.setTableId(-1L);
        record.setStatus(request.getStatus());
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        if(request.getStatus() == ApproveStatus.FirstApproveNotPass.getStatus() || request.getStatus() == ApproveStatus.FirstApprovePass.getStatus()){
            record.setFirstApproveAt(new Timestamp(System.currentTimeMillis()));
            record.setFirstApproveBy(user.getId());
            record.setFirstApproveComment(request.getComment());
        }
        if(request.getStatus() == ApproveStatus.FinalApproveNotPass.getStatus() || request.getStatus() == ApproveStatus.FinalApprovePass.getStatus()){
            record.setFinalApproveAt(new Timestamp(System.currentTimeMillis()));
            record.setFinalApproveBy(user.getId());
            record.setFinalApproveComment(request.getComment());
        }
        recordService.update(record, null, null, null, null);
        return createResponse(Constant.SUCCESS, "成功", recordService.findOne(id));
    }

    @ApiOperation(value = "根据tableId, sampleId, planId 查询记录")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "tableId", value = "tableId", dataType = "Long", paramType = "query"),
            @ApiImplicitParam( name = "sampleId", value = "sampleId", dataType = "Long", paramType = "query"),
            @ApiImplicitParam( name = "planId", value = "planId", dataType = "Long", paramType = "query"),
    })
    @GetMapping("/info")
    public Map<String, Object> findByTableIdAndSampleIdAndPlanId(@RequestParam Long tableId,
                                                                 @RequestParam Long sampleId,
                                                                 @RequestParam Long planId){
        if(tableService.isExist(tableId) == false){
            return createResponse(Constant.FAIL, "tableId不存在", null);
        }
        if(sampleService.isExist(sampleId) == false){
            return createResponse(Constant.FAIL, "sampleId不存在", null);
        }
        if(planService.isExist(planId) == false){
            return createResponse(Constant.FAIL, "planId不存在", null);
        }
        Long id = recordService.findByTableIdAndSampleIdAndPlanId(tableId, sampleId, planId);
        if(id == null){
            Sample sample = (Sample) sampleService.findOne(sampleId).get("sample");
            Table table = tableService.findOne(tableId);
            ReportPlan plan = planService.findOne(planId);
            Record record = new Record();
            record.setTableId(tableId);
            record.setPlanId(planId);
            record.setSampleId(sampleId);
            record.setSampleName(sample.getQymcCn());
            record.setAreaCode(sample.getSsqx());
            record.setGroupId(plan.getGroupId());
            record.setPlanRound(plan.getRound());
            record.setTableName(table.getName());
            Map<String, Object> data = new HashMap<>();
            data.put("record", record);
            data.put("table", null);
            return createResponse(Constant.SUCCESS, "报表记录不存在", data);
        }
        return createResponse(Constant.SUCCESS, "成功", recordService.findOne(id));
    }

    @ApiOperation(value = "查看表1,与报表计划时间对比前一年是否已经填报")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "planId", value = "计划ID", dataType = "Long", paramType = "query")
    })
    @GetMapping("/isExistPriYearRecord")
    public Map<String, Object> isExistPriYear(@ApiIgnore HttpServletRequest httpRequest,
                                              @RequestParam Long planId){
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        Sample sample = sampleService.findByUserId(user.getId());
        if(sample == null){
            return createResponse(Constant.FAIL, "企业信息不完善", null);
        }
        if(planService.isExist(planId) == false){
            return createResponse(Constant.FAIL, "报表计划不存在", null);
        }
        ReportPlan plan = planService.findOne(planId);
        if(plan.getGroupId() != 1L){
            return createResponse(Constant.FAIL, "报表计划不对应", null);
        }
        Map<String, Object> data = recordService.findAll(null, sample.getId(), null, plan.getRound(), 1L, 1, 1000);
        Map<String, Object> isExist = new HashMap<>();
        isExist.put("isExist", false);
        if(data.get("list") == null){
            return createResponse(Constant.SUCCESS, "成功", isExist);
        }
        List<Record> records = (List<Record>) data.get("list");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(plan.getRound());
        int year = calendar.get(Calendar.YEAR);
        boolean flag = false;
        for(Record record : records){
            calendar.setTime(record.getPlanRound());
            int priYear = calendar.get(Calendar.YEAR);
            if(priYear + 1 == year){
                flag = true;
                break;
            }
        }
        isExist.put("isExist", flag);
        return createResponse(Constant.SUCCESS, "成功", isExist);
    }
}
