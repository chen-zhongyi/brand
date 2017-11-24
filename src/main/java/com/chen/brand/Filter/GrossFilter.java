package com.chen.brand.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@WebFilter( filterName = "crossFilter", urlPatterns = "/*")
public class GrossFilter implements Filter{

    @Value("${web.allow-origin}")
    private String allowOrigin;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException{
        System.err.println("1 -- crossFilter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = request.getHeader("Origin");
        System.err.println("url : " + request.getRequestURL().toString());
        System.err.println("path : " + request.getPathInfo());
        System.err.println("origin : " + origin);
        if(origin != null){
            for(String temp : allowOrigin.split(",")){
                if(origin.equals(temp)){
                    response.setHeader("Access-Control-Allow-Origin", temp);
                }
            }
        }else{
            response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8083");
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "86400"); //设置过期时间
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. response.setHeader("Expires", "0");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(200);
            return;
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy(){

    }
}
