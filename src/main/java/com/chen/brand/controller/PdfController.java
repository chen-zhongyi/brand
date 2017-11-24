package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.Enum.UserType;
import com.chen.brand.http.request.pdf.PdfApprove;
import com.chen.brand.http.request.pdf.PdfInsert;
import com.chen.brand.model.Brand;
import com.chen.brand.model.Pdf;
import com.chen.brand.model.Sample;
import com.chen.brand.model.User;
import com.chen.brand.service.BrandService;
import com.chen.brand.service.PdfService;
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
@RequestMapping("/api/pdfs")
@Api(value = "api -- PdfController", description = "pdf文档管理接口")
public class PdfController extends BaseController{

    @Autowired
    private PdfService pdfService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SampleService sampleService;

    @ApiOperation("创建")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PdfInsert", paramType = "body")
    })
    @PostMapping("")
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest,
                                      @Valid @RequestBody PdfInsert request,
                                      @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(brandService.isExist(request.getBrandId()) == false){
            return createResponse(Constant.FAIL, "品牌id不存在", null);
        }
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        Sample sample = sampleService.findByUserId(user.getId());
        Brand brand = brandService.findOne(request.getBrandId());
        if(brand.getSampleId() != sample.getId()){
            return createResponse(Constant.FAIL, "不能生成不属于该企业的品牌评审材料", null);
        }
        Pdf pdf = new Pdf();
        pdf.setBrandId(request.getBrandId());
        pdf.setUserId(user.getId());
        pdf.setStatus(ApproveStatus.NotApprove.getStatus());
        pdfService.insert(pdf);
        return createResponse(Constant.SUCCESS, "成功", pdf);
    }

    @ApiOperation("审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PdfApprove", paramType = "body")
    })
    @PutMapping("/{id}")
    public Map<String, Object> approve(@PathVariable Long id,
                                       @Valid @RequestBody PdfApprove request,
                                       @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(pdfService.isExist(id) == false){
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        Pdf pdf = new Pdf();
        pdf.setId(id);
        pdf.setStatus(request.getStatus());
        if(request.getStatus() >= 2 && request.getStatus() <= 3){
            pdf.setFirstComment(request.getComment());
        }
        if(request.getStatus() >= 4 && request.getStatus() <= 5){
            pdf.setFinalComment(request.getComment());
        }
        pdfService.update(pdf);
        return createResponse(Constant.SUCCESS, "成功", pdfService.findOne(id));
    }

    @ApiOperation("根据ID获取信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "path")
    })
    @GetMapping("/{id}")
    public Map<String, Object> findOne(@PathVariable Long id){
        if(pdfService.isExist(id) == false){
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", pdfService.findOne(id));
    }

    @ApiOperation("获取列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区县Code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sampleName", value = "企业名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态：1-未审核 2-初审未通过  3-初审通过 4-终审未通过 5-终审通过", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页，分页大小", dataType = "int", paramType = "query")
    })
    @GetMapping("")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(required = false) String areaCode,
                                       @RequestParam(required = false) String sampleName,
                                       @RequestParam(required = false) Long status,
                                       @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                       @RequestParam(required = false, defaultValue = "20") int pageSize){
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        Long type = user.getType();
        Long userId = null;
        if(type == UserType.QX_ADMIN.getCode()){
            areaCode = user.getAreaCode();
        }else if(type == UserType.USER.getCode()){
            userId = user.getId();
        }
        return createResponse(Constant.SUCCESS, "成功", pdfService.findAll(areaCode, sampleName, status, userId, pageNumber, pageSize));
    }
}
