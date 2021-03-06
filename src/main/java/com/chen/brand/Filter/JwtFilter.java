package com.chen.brand.Filter;

import com.chen.brand.Constant;
import com.chen.brand.controller.BaseController;
import com.chen.brand.model.Api;
import com.chen.brand.model.User;
import com.chen.brand.model.UserCookie;
import com.chen.brand.service.UserCookieService;
import com.chen.brand.service.UserService;
import com.chen.brand.sys.SysData;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
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

    @Autowired
    private UserCookieService cookieService;

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
                    long userId = -1;
                    try{
                        userId = Long.valueOf(userCookies[0]);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    User user = userService.findOne(userId);
                    UserCookie c = cookieService.find(userId, userCookie, getIpAddress(request));
                    if(user != null && c != null && user.getId().longValue() == c.getUserId().longValue()
                            && getMd5String(user.getPwd() + "," + user.getOtherStr()).equals(userCookies[1])){
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
    }

    @Override
    public void destroy(){

    }
}
