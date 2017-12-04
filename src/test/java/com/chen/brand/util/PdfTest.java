package com.chen.brand.util;

import com.chen.brand.Enum.ApproveStatus;
import com.chen.brand.mapper.ApproveJnzlMapper;
import com.chen.brand.mapper.BrandMapper;
import com.chen.brand.mapper.SampleMapper;
import com.chen.brand.model.*;
import com.chen.brand.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfTest {

    @Autowired
    private SampleMapper sampleMapper;

    @Autowired
    private BrandMapper brandMapper;

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
    private ApproveJnzlService jnzlService;

    @Autowired
    private ApproveXyjsService xyjsService;

    @Autowired
    private TableBaseServer baseServer;

    private static final Long USER_ID = 21L;

    private static final Long BRAND_ID = 5L;

    @Value("${web.upload-path}")
    private String uploadPath;

    @Test
    @Rollback
    public void testPdf(){

        Sample sample = sampleMapper.findByUserId(USER_ID);
        Map<String, Object> data = new HashMap<>();
        data.put("sample", sample);
        Brand brand = brandMapper.findOne(BRAND_ID);
        data.put("brand", brand);


        int year = 2017;
        data.put("year", year);

        /**
         * table_base 年报
         */
        TableBase base = baseServer.findByUserIdAndYearAndStatus(USER_ID, year + "", ApproveStatus.FinalApprovePass.getStatus());
        TableBase priBase = baseServer.findByUserIdAndYearAndStatus(USER_ID, (year - 1) + "", ApproveStatus.FinalApprovePass.getStatus());
        data.put("base", base);
        data.put("priBase", priBase);

        /**
         * 表4 企业专利获取情况
         */
        List<ApproveJnzl> jnzls = jnzlService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        data.put("jnzls", jnzls);
        /**
         * 表5 企业科技获奖情况
         */
        List<ApproveKjjl> kjjls = kjjlService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        data.put("kjjls", kjjls);

        /**
         * 表6 企业科技平台建设情况
         */
        List<ApprovePtjs> ptjss = ptjsService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        data.put("ptjss", ptjss);

        /**
         * 表7 企业标准制定情况
         */
        List<ApproveBzqc> bzqcs = bzqcService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        data.put("bzqcs", bzqcs);

        /**
         * 表8 企业质量体系建设情况
         */
        List<ApproveBzrz> bzrzs = bzrzService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        data.put("bzrzs", bzrzs);

        /**
         * 表9 报品牌项下的境外注册商标
         */
        List<ApproveJwsb> jwsbs = jwsbService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        data.put("jwsbs", jwsbs);

        /**
         * 表10 申报品牌项下的境外收购情况
         */
        List<ApproveJwpp> jwpps = jwppService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        data.put("jwpps", jwpps);

        /**
         * 表11 申报品牌项下的境外申请专利情况
         */
        List<ApproveJwzl> jwzls = jwzlService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        data.put("jwzls", jwzls);

        /**
         * 表12 企业全球化经营情况
         */
        List<ApproveQqh> qqhs = qqhService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        data.put("qqhs", qqhs);

        /**
         * 表13 企业信用体系建设
         */
        List<ApproveXyjs> xyjss = xyjsService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        Map<String, ApproveXyjs> map = new HashMap<>();
        for(ApproveXyjs xyjs : xyjss){
            map.put(xyjs.getCode(), xyjs);
        }
        data.put("xyjss", map);

        /**
         * 表14 企业荣誉与社会评价
         */
        List<ApproveRych> rychs = rychService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        data.put("rychs", rychs);

        /**
         * 表15 部门处罚情况说明
         */
        List<ApproveCfsm> cfsms = cfsmService.findByUserIdAndStatus(USER_ID, ApproveStatus.FinalApprovePass.getStatus());
        data.put("cfsms", cfsms);
        //PdfUtil.getPdf(data, "/Users/chenzhongyi/chen.pdf", uploadPath);
    }
}
