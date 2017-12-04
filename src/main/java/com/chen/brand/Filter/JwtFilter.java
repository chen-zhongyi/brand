package com.chen.brand.Filter;

import com.chen.brand.Constant;
import com.chen.brand.controller.BaseController;
import com.chen.brand.model.Api;
import com.chen.brand.model.User;
import com.chen.brand.service.ApiService;
import com.chen.brand.service.UserService;
import com.chen.brand.sys.SysData;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@Order(2)
//@WebFilter( filterName = "jwtFilter", urlPatterns = "/api/*")
public class JwtFilter extends BaseController implements Filter{

    //@Autowired
    //private ApiService apiService;

    @Autowired
    private SysData sysData;

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException{
        System.out.println("1 -- jwtFilter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getPathInfo();
        Object[] temp = getApi(path);
        path = (String) temp[0];
        Api api = sysData.getApi(path);
        if(api != null && api.getSystemCode().equals("public")){
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpSession session = request.getSession();
        if(session.getAttribute(Constant.SESSION_NAME) == null){
            Cookie[] cookies = request.getCookies();
            System.out.println("cookie : " + new Gson().toJson(cookies));
            String userCookie = "";
            if(cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(Constant.COOKIE_NAME)) {
                        userCookie = cookie.getValue();
                        break;
                    }
                }
            }
            boolean isEnable = false;
            if( ! userCookie.equals(""))  {
                String[] userCookies = userCookie.split("-");
                if(userCookies.length == 2){
                    User user = userService.findByUserName(userCookies[0]);
                    if(getMd5String(user.getPwd() + "," + user.getOtherStr()).equals(userCookies[1])){
                        session.setAttribute(Constant.SESSION_NAME, user);
                        isEnable = true;
                    }
                }
            }
            if(! isEnable){
                Map<String, Object> data = new HashMap<>();
                data.put("code", Constant.FAIL);
                data.put("msg", "无效token");
                data.put("data", null);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(new Gson().toJson(data));
                response.getWriter().close();
                response.getWriter().flush();
                return ;
            }

        }
        chain.doFilter(servletRequest, servletResponse);
/*
        if(request.getPathInfo().startsWith("/api")){
            Cookie[] cookies = request.getCookies();
            System.out.println("cookie : " + new Gson().toJson(cookies));
            String jwt = "";
            if(cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(Constant.COOKIE_NAME)) {
                        jwt = cookie.getValue();
                        break;
                    }
                }
            }
            System.out.println("jwt : " + jwt);
            if(jwt.equals("")){
                Map<String, Object> data = new HashMap<>();
                data.put("code", Constant.FAIL);
                data.put("msg", "无效token");
                data.put("data", null);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(new Gson().toJson(data));
                response.getWriter().close();
                response.getWriter().flush();
                return ;
            }
            try{
                Jwts.parser().setSigningKey(Constant.JWT_SECRET).parseClaimsJws(jwt);
                chain.doFilter(servletRequest, servletResponse);
            }catch (SignatureException e){
                Map<String, Object> data = new HashMap<>();
                data.put("code", Constant.FAIL);
                data.put("msg", "token验证失败");
                data.put("data", null);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(new Gson().toJson(data));
                response.getWriter().close();
                response.getWriter().flush();
                return ;
            }
        }else {
            chain.doFilter(servletRequest, servletResponse);
        }*/
    }

    @Override
    public void destroy(){

    }
}
