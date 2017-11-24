package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.http.request.menu.MenuInsert;
import com.chen.brand.http.request.menu.MenuUpdate;
import com.chen.brand.model.Menu;
import com.chen.brand.model.User;
import com.chen.brand.service.MenuService;
import com.chen.brand.service.SysService;
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
@RequestMapping( value = "/api/menus")
@Api(value = "api -- menuController", description = "菜单管理接口")
public class MenuController extends BaseController{

    @Autowired
    private MenuService menuService;

    @Autowired
    private SysService sysService;

    @ApiOperation(value = "创建菜单")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "menuRequest", value = "请求实体类", dataType = "MenuInsert", paramType = "body")
    })
    @PostMapping( value = "")
    public Map<String, Object> insert(@RequestBody @Valid MenuInsert menuRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(menuService.isExist(menuRequest.getPid()) == false && menuRequest.getPid() != 0){
            return createResponse(Constant.FAIL, "父id不存在", null);
        }
        if(sysService.isExistCode(menuRequest.getSystem()) == false){
            return createResponse(Constant.FAIL, "系统代码不存在", null);
        }
        Menu menu = new Menu();
        menu.setName(menuRequest.getName());
        menu.setPid(menuRequest.getPid());
        menu.setUrl(menuRequest.getUrl());
        menu.setSort(menuRequest.getSort());
        menu.setImage(menuRequest.getImage());
        menu.setSystem(menuRequest.getSystem());
        menu.setType(menuRequest.getType());
        menu.setStatus(true);
        Long id = menuService.insert(menu);
        return createResponse(Constant.SUCCESS, "成功", menuService.findOne(id));
    }

    @ApiOperation(value = "修改菜单")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "菜单id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam( name = "menuRequest", value = "请求实体类", dataType = "MenuInsert", paramType = "body")
    })
    @PutMapping(value = "/{id}")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody @Valid MenuUpdate menuRequest, @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(menuService.isExist(id) == false){
            return createResponse(Constant.FAIL, "菜单不存在", null);
        }
        if(menuRequest.getPid() != null && menuService.isExist(menuRequest.getPid()) == false){
            return createResponse(Constant.FAIL, "父id不存在", null);
        }
        if(sysService.isExistCode(menuRequest.getSystem()) == false){
            return createResponse(Constant.FAIL, "系统代码不存在", null);
        }
        Menu menu = new Menu();
        menu.setName(menuRequest.getName());
        menu.setPid(menuRequest.getPid());
        menu.setUrl(menuRequest.getUrl());
        menu.setSort(menuRequest.getSort());
        menu.setImage(menuRequest.getImage());
        menu.setSystem(menuRequest.getSystem());
        menu.setType(menuRequest.getType());
        menu.setId(id);
        menuService.update(menu);
        return createResponse(Constant.SUCCESS, "成功", menuService.findOne(id));
    }

    @ApiOperation(value = "根据id查询菜单")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "菜单id", dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable Long id){
        if(menuService.isExist(id) == false){
            return createResponse(Constant.FAIL, "菜单不存在", null);
        }
        return createResponse(Constant.SUCCESS, "成功", menuService.findOne(id));
    }

    @ApiOperation(value = "查询用户菜单列表")
    @GetMapping(value = "")
    public Map<String, Object> findAll(@ApiIgnore HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(Constant.SESSION_NAME);
        Long userType = user.getType();
        String type = null;
        if(userType == Constant.QX_ADMIN || userType == Constant.ADMIN){
            type = "admin";
        }else if(userType == Constant.USER){
            type = "user";
        }
        return createResponse(Constant.SUCCESS, "成功", menuService.findAll(type));
    }

    @ApiOperation(value = "根据id删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "id", value = "菜单id", dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/{id}")
    public Map<String, Object> delete(@PathVariable Long id){
        if(menuService.isExist(id) == false){
            return createResponse(Constant.FAIL, "菜单不存在", null);
        }
        menuService.delete(id);
        return createResponse(Constant.SUCCESS, "成功", null);
    }
}