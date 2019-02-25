package com.chen.brand.Filter;

import com.chen.brand.Constant;
import com.chen.brand.Enum.RoleType;
import com.chen.brand.Enum.UserType;
import com.chen.brand.sys.SysData;
import com.chen.brand.controller.BaseController;
import com.chen.brand.model.Api;
import com.chen.brand.model.User;
import com.chen.brand.service.ApiService;
import com.chen.brand.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@Order(2)
//@WebFilter(filterName = "roleFilter", urlPatterns = "/api/*")
public class RoleFilter extends BaseController implements Filter{

    @Autowired
    private ApiService apiService;

    @Autowired
    private UserService userService;

    @Autowired
    private SysData sysData;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException{
        System.out.println("2 -- roleFilter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String method = request.getMethod().toUpperCase();
        String path = request.getPathInfo();
        Object[] temp = getApi(path);
        path = (String) temp[0];
        Long id = null;
        if(temp[1] != null)    id = (Long) temp[1];
        System.out.println("path = " + path + ", id = " + id + ", method = " + method);
        //Api api = apiService.findByApi(path);
        Api api = sysData.getApi(path);
        if(api == null || api.getStatus() == false){
            Map<String, Object> data = new HashMap<>();
            data.put("code", Constant.FAIL);
            data.put("msg", "URL出错");
            data.put("data", null);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(new Gson().toJson(data));
            response.getWriter().close();
            response.getWriter().flush();
            return ;
        }
        System.out.println("api = " + new Gson().toJson(api));
        if( ! ((api.getSystemCode().equals("sys") || api.getSystemCode().equals("public")) && method.equals("GET") )){  //非公共接口和系统获取接口
            User user = (User) request.getSession().getAttribute(Constant.SESSION_NAME);
            String rightStr = user.getRight();
            if(user.getType() == UserType.USER.getCode()){
                rightStr = sysData.findByCode(RoleType.USER.code()).getRight();
            }
            Map<String, String> right = new Gson().fromJson(rightStr, new TypeToken<Map<String, Object>>(){}.getType());
            System.out.println("right = " + new Gson().toJson(right));
            boolean flag = false;
            String type = right.get(api.getSystemCode());
            if(type != null){
                type = type.toUpperCase();
                if(type.equals("VIEW")){
                    if(!method.equals("GET"))   flag = true;
                }else if(! type.equals("EDIT")) flag = true;
            }else flag = true;
            /*if( ! flag ) {
                if (user.getType() == UserType.USER.getCode()) {
                    if (id != null && user.getId() != id) flag = true;
                } else if (user.getType() == UserType.QX_ADMIN.getCode()) {
                    if (id != null) {
                        User u = userService.findOne(id);
                        if (u.getAreaCode() != user.getAreaCode()) flag = true;
                    }
                }
            }*/
            System.out.println("flag = " + flag);
            if(flag){
                Map<String, Object> data = new HashMap<>();
                data.put("code", Constant.FAIL);
                data.put("msg", "权限不够");
                data.put("data", null);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(new Gson().toJson(data));
                response.getWriter().close();
                response.getWriter().flush();
                return ;
            }
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy(){

    }
}
