package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.Enum.RoleType;
import com.chen.brand.http.request.Role.RoleInsert;
import com.chen.brand.http.request.Role.RoleUpdate;
import com.chen.brand.model.Role;
import com.chen.brand.service.RoleService;
import com.chen.brand.service.SysService;
import com.chen.brand.sys.SysData;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/roles")
@Api(value = "api -- roleController", description = "角色管理接口")
public class RoleController extends BaseController{

    @Autowired
    private RoleService roleService;

    @Autowired
    private SysService sysService;

    @Autowired
    private SysData sysData;

    @ApiOperation(value = "创建角色")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "roleRequest", value = "请求实体类", dataType = "RoleInsert", paramType = "body")
    })
    @PostMapping("")
    public Map<String, Object> insert(@RequestBody @Valid RoleInsert roleRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        RoleType roleType = RoleType.convert(roleRequest.getCode());
        if(roleType == null || roleService.isExistCode(roleRequest.getCode())){
            return createResponse(Constant.FAIL, "code已存在或者code不合法", null);
        }
        String msg = checkRight(roleRequest.getRight());
        if(msg.equals("") == false){
            return createResponse(Constant.FAIL, msg, null);
        }
        Role role = new Role();
        role.setCode(roleRequest.getCode());
        role.setDescription(roleRequest.getDescription());
        role.setRight(new Gson().toJson(roleRequest.getRight()));
        role.setStatus(true);
        Long id = roleService.insert(role);
        sysData.loadRole();
        return createResponse(Constant.SUCCESS, "成功", roleService.findOne(id).buildResponse());
    }

    @ApiOperation(value = "修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "角色id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam( name = "roleRequest", value = "请求实体类", dataType = "RoleUpdate", paramType = "body")
    })
    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid RoleUpdate roleRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(roleService.isExist(id) == false){
            return createResponse(Constant.FAIL, "角色不存在", null);
        }
        String msg = checkRight(roleRequest.getRight());
        if(msg.equals("") == false){
            return createResponse(Constant.FAIL, msg, null);
        }
        Role role = new Role();
        role.setRight(new Gson().toJson(roleRequest.getRight()));
        role.setId(id);
        roleService.update(role);
        sysData.loadRole();
        return createResponse(Constant.SUCCESS, "成功", roleService.findOne(id).buildResponse());
    }

    @ApiOperation(value = "根据id获取角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "角色id", dataType = "Long", paramType = "path")
    })
    @GetMapping("/{id}")
    public Map<String, Object> findOne(@PathVariable Long id){
        if(roleService.isExist(id) == false){
            return createResponse(Constant.FAIL, "角色不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", roleService.findOne(id).buildResponse());
    }

    @ApiOperation(value = "获取角色信息列表")
    @GetMapping()
    public Map<String, Object> findAll(){
        List<Map<String, Object>> data = new LinkedList<>();
        List<Role> roles = roleService.findAll();
        for(Role role : roles){
            data.add(role.buildResponse());
        }
        return createResponse(Constant.SUCCESS, "成功", data);
    }

    @ApiOperation(value = "禁用启用角色")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "角色id", dataType = "Long", paramType = "path")
    })
    @PutMapping( value = "/changeStatus/{id}")
    public Map<String, Object> changeStatus(@PathVariable Long id){
        if(roleService.isExist(id) == false){
            return createResponse(Constant.FAIL, "角色不存在", null);
        }
        Role role = roleService.findOne(id);
        role.setStatus(role.getStatus() == true ? false : true);
        roleService.changeStatus(role.getId(), role.getStatus());
        return createResponse(Constant.SUCCESS, "成功", roleService.findOne(id).buildResponse());
    }

    private String checkRight(Map<String, String> rights){
        StringBuffer msg = new StringBuffer("");
        String msg1 = "";
        for(String sysCode : rights.keySet()){
            if(sysService.isExistCode(sysCode) == false){
                msg.append(sysCode + " ");
            }
            String right = rights.get(sysCode);
            if(!(right.equals("edit") || right.equals("view") || right.equals("nothing"))){
                msg1 = "权限只能是edit、view、nothing其中之一";
            }
        }
        if(msg.toString().equals("") == false || msg1.equals("") == false){
            return msg.toString() + "系统代码不存在; " + msg1;
        }
        return "";
    }
}
