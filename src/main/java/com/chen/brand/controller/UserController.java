package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.Enum.RoleType;
import com.chen.brand.http.request.User.UserInsert;
import com.chen.brand.http.request.User.UserNewPwd;
import com.chen.brand.http.request.User.UserUpdate;
import com.chen.brand.model.User;
import com.chen.brand.service.AreaService;
import com.chen.brand.service.RoleService;
import com.chen.brand.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping( value = "/api/users" )
@Api(value = "API - UserController", description = "用户管理接口")
public class UserController extends BaseController{

    @Autowired
    private AreaService areaService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userRequest", value = "用户添加请求参数实体类", required = true, dataType = "UserInsert")
    })
    @RequestMapping( value = "", method = RequestMethod.POST )
    public Map<String, Object> insert(@ApiIgnore HttpServletRequest httpRequest, @RequestBody @Valid UserInsert userRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(userService.isExistUserName(userRequest.getUserName())){
            return createResponse(Constant.FAIL, "用户名已存在", null);
        }
        if(areaService.isExist(userRequest.getAreaCode()) == false){
            return createResponse(Constant.FAIL, "区县代码不存在", null);
        }
        if(checkUserName(userRequest.getUserName()) == false){
            return createResponse(Constant.FAIL, "用户名包含非法字符" + Arrays.toString(illegalChar), null);
        }
        User user = new User();
        user.setAreaCode(userRequest.getAreaCode());
        user.setUserName(userRequest.getUserName());
        user.setRealName(userRequest.getRealName());
        user.setOtherStr(getOtherStr());
        user.setPwd(getMd5String(userRequest.getPwd() + user.getOtherStr()));
        User session = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        user.setCreateBy(session.getId());
        user.setCreateAt(new Timestamp(System.currentTimeMillis()));
        user.setType(Constant.QX_ADMIN);
        user.setStatus(true);
        user.setRole(RoleType.QX_ADMIN.code());
        user.setRight(roleService.findByCode(RoleType.QX_ADMIN.code()).getRight());
        Long id = userService.insert(user);
        return createResponse(Constant.SUCCESS, "成功", userService.findOne(id).buildResponse());
    }

    @ApiOperation( value = "修改用户基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "用户id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam( name = "userRequest", value = "用户请求实体类", required = true, dataType = "UserUpdate")
    })
    @RequestMapping( value = "/{id}", method = RequestMethod.PUT)
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid UserUpdate userRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(userService.isExist(id) == false){
            return createResponse(Constant.FAIL, "用户不存在", null);
        }
        User user = userService.findOne(id);
        user.setRealName(userRequest.getRealName());
        userService.update(user);
        return createResponse(Constant.SUCCESS, "成功", userService.findOne(id).buildResponse());
    }

    @ApiOperation( value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "用户id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam( name = "userRequest", value = "用户请求实体类", required = true, dataType = "UserNewPwd")
    })
    @RequestMapping( value = "/newPwd/{id}", method = RequestMethod.PUT)
    public Map<String, Object> newPwd(@PathVariable Long id, @RequestBody @Valid UserNewPwd userRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(userService.isExist(id) == false){
            return createResponse(Constant.FAIL, "用户不存在", null);
        }
        User user = userService.findOne(id);
        if(userService.checkPwd(user.getUserName(), getMd5String(userRequest.getPwd() + user.getOtherStr())) == false){
            return createResponse(Constant.FAIL, "用户不存在或者密码错误", null);
        }
        user.setPwd(getMd5String(userRequest.getNewPwd() + user.getOtherStr()));
        userService.update(user);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation( value = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "用户id", required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping( value = "/resetPwd/{id}", method = RequestMethod.PUT)
    public Map<String, Object> resetPwd(@PathVariable Long id){
        if(userService.isExist(id) == false){
            return createResponse(Constant.FAIL, "用户不存在", null);
        }
        User user = userService.findOne(id);
        user.setPwd(getMd5String("123456" + user.getOtherStr()));
        userService.update(user);
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    @ApiOperation( value = "根据id获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "用户id", required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping( value = "/{id}", method = RequestMethod.GET)
    public Map<String, Object> getUser(@PathVariable Long id){
        if(userService.isExist(id) == false){
            return createResponse(Constant.FAIL, "用户不存在", null);
        }
        User user = userService.findOne(id);
        return createResponse(Constant.SUCCESS, "成功", user.buildResponse());
    }

    @ApiOperation( value = "获取用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "areaCode", value = "区县Code", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam( name = "realName", value = "模糊查询根据姓名匹配", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam( name = "type", value = "用户类型", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam( name = "pageNumber", value = "分页，第几页", required = false, defaultValue = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam( name = "pageSize", value = "分页，页面大小", required = false, defaultValue = "20", dataType = "int", paramType = "query")
    })
    @RequestMapping( value = "", method = RequestMethod.GET)
    public Map<String, Object> getUsers(@ApiIgnore HttpServletRequest httpRequest,
                                        @RequestParam(required = false) String areaCode,
                                        @RequestParam(required = false) String realName,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(defaultValue = "1") int pageNumber,
                                        @RequestParam(defaultValue = "20") int pageSize){
        User session = (User) httpRequest.getSession().getAttribute(Constant.SESSION_NAME);
        Long userId = null;
        Long userType = session.getType();
        if(userType == Constant.QX_ADMIN){
            areaCode = session.getAreaCode();
        }else if(userType == Constant.USER){
            userId = session.getId();
        }
        Map<String, Object> map = new HashMap<>();
        List<User> users = userService.findAll(areaCode, userId, realName, type, pageNumber, pageSize);
        List<Map<String, Object>> temp = new LinkedList<>();
        for(User user : users){
            temp.add(user.buildResponse());
        }
        map.put("list", temp);
        map.put("total", userService.count(areaCode, userId, realName, type));
        return createResponse(Constant.SUCCESS, "成功", map);
    }

    @ApiOperation( value = "禁用启用用户", notes = "该接口将status由1变0或者由0变1")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "用户id", required = true, dataType = "Long", paramType = "path")
    })
    @RequestMapping( value = "/setStatus/{id}", method = RequestMethod.PUT)
    public Map<String, Object> setStatus(@PathVariable Long id){
        if(userService.isExist(id) == false){
            return createResponse(Constant.FAIL, "用户不存在", null);
        }
        User user = userService.findOne(id);
        user.setStatus(user.getStatus() == true ? false : true);
        userService.update(user);
        return createResponse(Constant.SUCCESS, "成功", userService.findOne(id).buildResponse());
    }

}
