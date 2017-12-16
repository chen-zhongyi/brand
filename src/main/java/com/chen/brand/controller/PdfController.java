package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.Enum.UserType;
import com.chen.brand.http.request.pdf.PdfApprove;
import com.chen.brand.http.request.pdf.PdfInsert;
import com.chen.brand.model.*;
import com.chen.brand.service.*;
import com.chen.brand.util.PdfUtil;
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
import java.util.HashMap;
import java.util.List;
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

    @Value("${web.upload-path}")
    private String UPLOAD_PATH;

    @ApiOperation("是否已经创建了文档")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PdfInsert", paramType = "body")
    })
    @GetMapping("/isCreated")
    public Map<String, Object> isCreated(@ApiIgnore HttpServletRequest httpRequest,
                                        @RequestParam Long brandId){
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        return createResponse(Constant.SUCCESS, "成功", pdfService.findByUserIdAndBrandId(user.getId(), brandId));
    }

    @ApiOperation("生成pdf文档接口,未生成文档的创建文档，已生成文档的更新文档内容")
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
        Pdf pdf = pdfService.findByUserIdAndBrandId(user.getId(), request.getBrandId());
        String name = "pdfs" + File.separator + sample.getQymcCn() + "-" + brand.getPpmc() + "-" + System.currentTimeMillis() + ".pdf";
        String path = UPLOAD_PATH + File.separator + "pdfs" + File.separator + name;
        PdfUtil.getPdf(getData(user.getId(), brand.getId()), path, UPLOAD_PATH);
        if(pdf == null){
            pdf = new Pdf();
            pdf.setBrandId(request.getBrandId());
            pdf.setUserId(user.getId());
            pdf.setStatus(ApproveStatus.NotApprove.getStatus());
            pdf.setPath(name);
            pdfService.insert(pdf);
        }else{
            pdf.setPath(name);
            pdfService.update(pdf);
        }
        return createResponse(Constant.SUCCESS, "成功", pdf);
    }

    @ApiOperation("审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "request", value = "请求体", dataType = "PdfApprove", paramType = "body")
    })
    @PutMapping("/approve/{id}")
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
        if(request.getStatus() == ApproveStatus.FirstApproveNotPass.getStatus() || request.getStatus() == ApproveStatus.FirstApprovePass.getStatus()){
            pdf.setFirstComment(request.getComment());
        }
        if(request.getStatus() == ApproveStatus.FinalApproveNotPass.getStatus() || request.getStatus() <= ApproveStatus.FinalApprovePass.getStatus()){
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
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页，分页大小", dataType = "int", paramType = "query", defaultValue = "20")
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

    @Autowired
    private ApproveJnzlService jnzlService;

    @Autowired
    private ApproveKjjlService kjjlService;

    @Autowired
    private ApprovePtjsService ptjsService;

    @Autowired
    private ApproveBzqcService bzqcService;

    @Autowired
    private ApproveBzrzService bzrzService;

    @Autowired
    private ApproveJwsbService jwsbService;

    @Autowired
    private ApproveJwzlService jwzlService;

    @Autowired
    private ApproveJwppService jwppService;

    @Autowired
    private ApproveQqhService qqhService;

    @Autowired
    private ApproveRychService rychService;

    @Autowired
    private ApproveCfsmService cfsmService;

    @Autowired
    private ApproveXyjsService xyjsService;

    @Autowired
    private ApproveZmclService zmclService;

    @Autowired
    private TableBaseServer baseServer;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ApprovePpckeService ppckeService;

    private Map<String, Object> getData(Long userId, Long brandId){
        Map<String, Object> data = new HashMap<>();
        /**
         * 年份
         */
        int year = 2017;
        data.put("year", year);

        /**
         * 企业基础数据
         */
        Sample sample = sampleService.findByUserId(userId);
        Area area = areaService.findOne(sample.getSsqx() == null ? "" : sample.getSsqx());
        sample.setSsqx(area == null ? null : area.getName());
        data.put("sample", sample);

        /**
         * 品牌基础数据
         */
        Brand brand = brandService.findOne(brandId);
        data.put("brand", brand);

        /**
         * 品牌出口额
         */
        ApprovePpcke ppcke = ppckeService.findByUserIdAndBrandIdAndYearAndStatus(userId, brandId, year + "", ApproveStatus.FinalApprovePass.getStatus());
        ApprovePpcke priPpcke = ppckeService.findByUserIdAndBrandIdAndYearAndStatus(userId, brandId, (year - 1) + "", ApproveStatus.FinalApprovePass.getStatus());
        data.put("ppcke", ppcke);
        data.put("priPpcke", priPpcke);

        /**
         * table_base 年报
         */
        TableBase base = baseServer.findByUserIdAndYearAndStatus(userId, year + "", ApproveStatus.FinalApprovePass.getStatus());
        TableBase priBase = baseServer.findByUserIdAndYearAndStatus(userId, (year - 1) + "", ApproveStatus.FinalApprovePass.getStatus());
        data.put("base", base);
        data.put("priBase", priBase);

        /**
         * 证明材料
         */
        ApproveZmcl zmcl = zmclService.findByUserIdAndYearAndStatus(userId, year + "", ApproveStatus.FinalApprovePass.getStatus());
        data.put("zmcl", zmcl);

        /**
         * 表4 企业专利获取情况
         */
        List<ApproveJnzl> jnzls = jnzlService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        data.put("jnzls", jnzls);

        /**
         * 表5 企业科技获奖情况
         */
        List<ApproveKjjl> kjjls = kjjlService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        data.put("kjjls", kjjls);

        /**
         * 表6 企业科技平台建设情况
         */
        List<ApprovePtjs> ptjss = ptjsService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        data.put("ptjss", ptjss);

        /**
         * 表7 企业标准制定情况
         */
        List<ApproveBzqc> bzqcs = bzqcService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        data.put("bzqcs", bzqcs);

        /**
         * 表8 企业质量体系建设情况
         */
        List<ApproveBzrz> bzrzs = bzrzService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        data.put("bzrzs", bzrzs);

        /**
         * 表9 报品牌项下的境外注册商标
         */
        List<ApproveJwsb> jwsbs = jwsbService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        data.put("jwsbs", jwsbs);

        /**
         * 表10 申报品牌项下的境外收购情况
         */
        List<ApproveJwpp> jwpps = jwppService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        data.put("jwpps", jwpps);

        /**
         * 表11 申报品牌项下的境外申请专利情况
         */
        List<ApproveJwzl> jwzls = jwzlService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        data.put("jwzls", jwzls);

        /**
         * 表12 企业全球化经营情况
         */
        List<ApproveQqh> qqhs = qqhService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        data.put("qqhs", qqhs);

        /**
         * 表13 企业信用体系建设
         */
        List<ApproveXyjs> xyjss = xyjsService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        Map<String, ApproveXyjs> map = new HashMap<>();
        for(ApproveXyjs xyjs : xyjss){
            map.put(xyjs.getCode(), xyjs);
        }
        data.put("xyjss", map);
        /**
         * 表14 企业荣誉与社会评价
         */
        List<ApproveRych> rychs = rychService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        data.put("rychs", rychs);

        /**
         * 表15 部门处罚情况说明
         */
        List<ApproveCfsm> cfsms = cfsmService.findByUserIdAndStatus(userId, ApproveStatus.FinalApprovePass.getStatus());
        data.put("cfsms", cfsms);
        return data;
    }
}
