package com.chen.brand.controller;

import com.chen.brand.Constant;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileUploadController extends BaseController{

    @Value("${web.upload-path}")
    private String UPLOAD_PATH;

    @RequestMapping( value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam MultipartFile file) throws IOException {
        System.out.println("path : " + UPLOAD_PATH);
        Map<String, String> data = new HashMap<>();
        if(file.isEmpty() == false) {
            String fileName = file.getName();
            String name = file.getOriginalFilename();
            String contextType = file.getContentType();
            String suffix = getSuffix(name);
            System.out.println("name = " + name + ", file = " + fileName + ", contextType = " + contextType + ", suffix = " + suffix);
            if(contextType.contains("image")) {
                if(suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".png") || suffix.equals(".gif")) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHssmm");
                    String imagePath = "images" + File.separator + sf.format(new Date(System.currentTimeMillis())) + suffix;
                    File image = new File(UPLOAD_PATH + imagePath);
                    if(! image.exists())		image.getParentFile().mkdirs();
                    FileUtils.copyInputStreamToFile(file.getInputStream(), image);
                    data.put("path", imagePath);
                }else {
                    return createResponse(Constant.FAIL, "暂不支持非.jpg, .jpeg, .png, .gif格式的图片上传", null);
                }
            }else {
                return createResponse(Constant.FAIL, "暂不支持非图片文件上传", null);
            }
        }else {
            return createResponse(Constant.FAIL, "文件为空", null);
        }
        return createResponse(Constant.SUCCESS, "成功", data);
    }

    private String getSuffix(String name) {
        if(name.lastIndexOf(".") != -1) {
            return name.substring(name.lastIndexOf("."), name.length()).toLowerCase();
        }
        return "";
    }
}
