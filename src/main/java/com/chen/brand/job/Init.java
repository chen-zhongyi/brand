package com.chen.brand.job;

import com.chen.brand.Enum.RoleType;
import com.chen.brand.Enum.UserType;
import com.chen.brand.model.Area;
import com.chen.brand.model.Brand;
import com.chen.brand.model.Sample;
import com.chen.brand.model.User;
import com.chen.brand.service.BrandService;
import com.chen.brand.service.RoleService;
import com.chen.brand.service.SampleService;
import com.chen.brand.service.UserService;
import com.chen.brand.sys.SysData;
import com.chen.brand.util.ExcelUtils;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.chen.brand.controller.BaseController.getMd5String;
import static com.chen.brand.controller.BaseController.getOtherStr;

@Component
public class Init implements CommandLineRunner{

    private static final Logger logger = Logger.getLogger(Init.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SysData sysData;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    public void run(String... args) throws Exception{
        logger.info("[server start] dbUrl : " + getDbUrl());
        sysData.init();
        //init();
        //initSample();
    }

    private void initSample() throws Exception {
        Resource resource = new ClassPathResource("samples.xlsx");
        List<List<String>> datas = ExcelUtils.read(resource.getFile());
        System.out.println(datas);
        Long userId = 31L;
        for (int i = 1; i < datas.size(); ++i) {
            List<String> data = datas.get(i);
            Sample sample = sampleService.findByUserId(userId);
            if (sample != null) {
                sample.setQymcEn(data.get(2));
                sample.setFrdb(data.get(4));
                sample.setXydm(data.get(5));
                sample.setZcdz(data.get(6));
                sample.setLxr(data.get(7));
                sample.setLxsj(data.get(8));
                sample.setLxdh(data.get(9));
                sample.setQylx(data.get(10));
                sample.setQysyzxz(data.get(11));
                sample.setDzsw(data.get(12).equalsIgnoreCase("是"));
                sample.setWz(data.get(13));
                sample.setQyjj(data.get(14));
                sample.setZczb(Double.parseDouble(data.get(15)));
                sample.setBgdz(data.get(16));
                System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(sample));
                sampleService.update(sample, new ArrayList<>());
                userId++;
            }
        }
    }

    private void init() throws Exception {
        Resource resource = new ClassPathResource("init.txt");
        File file = resource.getFile();
        List<String> lines = FileUtils.readLines(file, "utf-8");
        System.out.println(lines);
        for (int i = 0; i < lines.size(); i += 4) {
            String index = lines.get(i);
            String areaName = lines.get(i + 1);
            String sampleName = lines.get(i + 2);
            String brandName = lines.get(i + 3);
            System.out.println(index + ":" + areaName + "-" + sampleName + "-" + brandName);
            Area area = sysData.getAreaByName(areaName);
            System.out.println(area.getCode());
            User user = new User();
            user.setUserName("username" + index.trim());
            user.setAreaCode(area.getCode());
            user.setOtherStr(getOtherStr());
            user.setPwd(getMd5String("123456" + user.getOtherStr()));
            user.setRole(RoleType.USER.code());
            user.setType(UserType.USER.getCode());
            user.setStatus(true);
            user.setCreateAt(new Timestamp(System.currentTimeMillis()));
            user.setCreateBy(-1L);
            user.setRight(roleService.findByCode(RoleType.USER.code()).getRight());
            Long userId = userService.insert(user);
            Sample sample = new Sample();
            sample.setQymcCn(sampleName);
            sample.setSsqx(area.getCode());
            sample.setStatus(0L);
            sample.setUserId(userId);
            Long sampleId = sampleService.insert(sample, new ArrayList<>());
            Brand brand = new Brand();
            brand.setPpmc(brandName);
            brand.setStatus(1L);
            brand.setUserId(userId);
            brand.setSampleId(sampleId);
            brandService.insert(brand);
        }
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }
}
