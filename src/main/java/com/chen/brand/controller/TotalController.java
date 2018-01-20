package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.Enum.UserType;
import com.chen.brand.model.*;
import com.chen.brand.service.*;
import com.chen.brand.util.PdfUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/total")
@Api(value = "api -- TotalController", description = "数据汇总管理接口")
public class TotalController extends BaseController{

    @Autowired
    private TotalService totalService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private PdfService pdfService;

    @Value("${web.upload-path}")
    private String UPLOAD_PATH;

    @Value("${os}")
    private String os;

    @ApiOperation("汇总数据是否提交")
    @GetMapping("/isCommit")
    public Map<String, Object> isCommit(@ApiIgnore HttpServletRequest httpRequest){
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        Total total = totalService.findByUserId(user.getId());
        boolean isCommit = total == null ? false : total.getSure();
        return createResponse(Constant.SUCCESS, "成功", isCommit);
    }

    @ApiOperation("创建汇总记录")
    @PostMapping("")
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest){
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        if(user.getType() == UserType.USER.getCode()){
            Total total = totalService.findByUserId(user.getId());
            if(total == null){
                Total t = new Total();
                t.setUserId(user.getId());
                t.setSure(false);
                total = totalService.insert(t);
            }
            Map<String, Object> data = new HashMap<>();
            data.put("total", total);
            data.put("data", all(user.getId(), "2016"));
            return createResponse(Constant.SUCCESS, "成功", data);
        }
        return createResponse(Constant.FAIL, "失败", null);
    }

    @ApiOperation("申报")
    @PostMapping("/declare")
    public Map<String, Object> createPdf(@ApiIgnore HttpServletRequest httpRequest){
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        if(user.getType() == UserType.USER.getCode()){
            Total total = totalService.findByUserId(user.getId());
            if(total == null){
                Total t = new Total();
                t.setUserId(user.getId());
                t.setSure(true);
                totalService.insert(t);
            }else {
                total.setSure(true);
                totalService.update(total);
            }
            List<Brand> brands = brandService.findByUserIdAndStatus(user.getId(), ApproveStatus.FinalApprovePass.getStatus());
            Sample sample = sampleService.findByUserId(user.getId());
            for(Brand brand : brands) {
                Pdf pdf = pdfService.findByUserIdAndBrandId(user.getId(), brand.getId());
                String name = sample.getQymcCn() + "-" + brand.getPpmc() + "-" + System.currentTimeMillis() + ".pdf";
                String path = UPLOAD_PATH + "pdfs" + File.separator + name;
                PdfUtil.getPdf(getData(user.getId(), brand.getId()), path, UPLOAD_PATH, os);
                if (pdf == null) {
                    pdf = new Pdf();
                    pdf.setBrandId(brand.getId());
                    pdf.setUserId(user.getId());
                    pdf.setStatus(ApproveStatus.NotApprove.getStatus());
                    pdf.setPath(name);
                    pdfService.insert(pdf);
                } else {
                    pdf.setPath(name);
                    pdfService.update(pdf);
                }
            }
        }
        return createResponse(Constant.SUCCESS, "申报成功", null);
    }

    @ApiOperation("是否确定，确定改成不确定，不确定改成确定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "path")
    })
    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable Long id, @ApiIgnore HttpServletRequest httpRequest){
        Total total = totalService.findOne(id);
        if(total == null){
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        total.setSure(total.getSure() == true ? false : true);
        totalService.update(total);
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        List<Brand> brands = brandService.findByUserIdAndStatus(user.getId(), ApproveStatus.FinalApprovePass.getStatus());
        for(Brand brand : brands) {
            Pdf pdf = pdfService.findByUserIdAndBrandId(user.getId(), brand.getId());
            if (pdf != null) {
                pdf.setStatus(ApproveStatus.NotApprove.getStatus());
                pdf.setFirstComment("");
                pdf.setFinalComment("");
                pdfService.update(pdf);
            }
        }
        //Map<String, Object> data = new HashMap<>();
        //data.put("total", total);
        //data.put("data", all(total.getUserId(), "2016"));
        return createResponse(Constant.SUCCESS, "成功", true);
    }

    @ApiOperation("根据ID，查看详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "path")
    })
    @GetMapping("/{id}")
    public Map<String, Object> findOne(@PathVariable Long id){
        Total total = totalService.findOne(id);
        if(total == null){
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("data", all(total.getUserId(), "2016"));
        return createResponse(Constant.SUCCESS, "成功", data);
    }

    @ApiOperation("查看列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区县Code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "int", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页，页面大小", dataType = "int", paramType = "query", defaultValue = "20")
    })
    @GetMapping("")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest httpRequest,
                                       @RequestParam(required = false) String areaCode,
                                       @RequestParam(required = false, defaultValue = "1") int pageNumber,
                                       @RequestParam(required = false, defaultValue = "20") int pageSize){
        User user = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        Long userId = null;
        if(user.getType() == Constant.USER){
            userId = user.getId();
        }else if(user.getType() == Constant.QX_ADMIN){
            areaCode = user.getAreaCode();
        }
        return createResponse(Constant.SUCCESS, "成功", totalService.findAll(userId, areaCode, pageNumber, pageSize));
    }

    @Autowired
    private ApproveBzqcService bzqcService;

    @Autowired
    private ApproveBzrzService bzrzService;

    @Autowired
    private ApproveCfsmService cfsmService;

    @Autowired
    private ApproveJnzlService jnzlService;

    @Autowired
    private ApproveJwppService jwppService;

    @Autowired
    private ApproveJwsbService jwsbService;

    @Autowired
    private ApproveJwzlService jwzlService;

    @Autowired
    private ApproveKjjlService kjjlService;

    @Autowired
    private ApprovePpckeService ppckeService;

    @Autowired
    private ApprovePtjsService ptjsService;

    @Autowired
    private ApproveQqhService qqhService;

    @Autowired
    private ApproveRychService rychService;

    @Autowired
    private ApproveXyjsService xyjsService;

    @Autowired
    private ApproveZmclService zmclService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private TableBaseServer baseServer;

    @Autowired
    private WzService wzService;

    private Map<String, int[]> all(Long userId, String year){
        Map<String, int[]> data = new HashMap<>();
        data.put("bzqc", bzqcService.total(userId));
        data.put("bzrz", bzrzService.total(userId));
        data.put("cfsm", cfsmService.total(userId));
        data.put("jnzl", jnzlService.total(userId));
        data.put("jwpp", jwppService.total(userId));
        data.put("jwsb", jwsbService.total(userId));
        data.put("jwzl", jwzlService.total(userId));
        data.put("kjjl", kjjlService.total(userId));
        data.put("ppcke", ppckeService.total(userId, year));
        data.put("ptjs", ptjsService.total(userId));
        data.put("qqh", qqhService.total(userId));
        data.put("rych", rychService.total(userId));
        data.put("xyjs", xyjsService.total(userId, year));
        data.put("zmcl", zmclService.total(userId, year));
        return data;
    }

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
        if(sample.getWz() != null){
            StringBuffer sb = new StringBuffer("");
            for(String wzId : sample.getWz().split(",")){
                try {
                    long id = Long.valueOf(wzId);
                    sb.append(wzService.findOne(id).getLink() + ", ");
                }catch(Exception e){
                    e.printStackTrace();
                    continue;
                }
            }
            sample.setWz(sb.toString());
        }
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
