package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.model.Total;
import com.chen.brand.model.User;
import com.chen.brand.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/total")
@Api(value = "api -- TotalController", description = "数据汇总管理接口")
public class TotalController extends BaseController{

    @Autowired
    private TotalService totalService;

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
        if(user.getType() == Constant.USER){
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

    @ApiOperation("是否确定，确定改成不确定，不确定改成确定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", dataType = "long", paramType = "path")
    })
    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable Long id){
        Total total = totalService.findOne(id);
        if(total == null){
            return createResponse(Constant.FAIL, "ID不存在", null);
        }
        total.setSure(total.getSure() == true ? false : true);
        totalService.update(total);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("data", all(total.getUserId(), "2016"));
        return createResponse(Constant.SUCCESS, "成功", data);
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
        return createResponse(Constant.SUCCESS, "ID不存在", data);
    }

    @ApiOperation("查看列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "区县Code", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pageNumber", value = "分页，第几页", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页，页面大小", dataType = "int", paramType = "query")
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
    
}
