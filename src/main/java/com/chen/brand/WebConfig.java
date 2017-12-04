package com.chen.brand;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.chen.brand.Filter.GrossFilter;
import com.chen.brand.Filter.JwtFilter;
import com.chen.brand.Filter.RoleFilter;
import com.chen.brand.log.LogManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return registration;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);

        //  初始化转换器
        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
        //  初始化一个转换器配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        //fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        //  将配置设置给转换器并添加到HttpMessageConverter转换器列表中
        fastConvert.setFastJsonConfig(fastJsonConfig);

        converters.add(fastConvert);
    }
    /**
     * 允许跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1L * 500 * 1024 * 1024);
        return multipartResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String osName = System.getProperty("os.name");
        System.err.println("osName --> " + osName);
        if(osName.contains("Mac"))
            registry.addResourceHandler("/**").addResourceLocations("file:/Users/chenzhongyi/upload/");
        else
            registry.addResourceHandler("/**").addResourceLocations("file:D:\\brand\\upload\\");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/admin/**")
                .addResourceLocations("classpath:/static/admin/");
    }

    @Bean(name = "xyjs")
    public Map<String, String> xyjsConstant(){
        Map<String, String> map = new HashMap<>();
        map.put("xy1", "企业进出口信用（海关总署）");
        map.put("xy2", "纳税信用（国税总局）");
        map.put("xy3", "出入境检验检疫企业信用（质检总局）");
        map.put("xy4", "国家企业信用（工商总局）");
        map.put("xy5", "市级以上信用管理示范");
        return map;
    }

    public static void main(String[] args){
        System.err.println(System.getProperty("os.name"));
    }

    @Bean
    public LogManager logManager(){
        return new LogManager();
    }

    @Bean
    public FilterRegistrationBean grossFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(grossFilter());
        registration.addUrlPatterns("/*");
        //registration.addInitParameter("paramName", "paramValue");
        registration.setName("grossFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public GrossFilter grossFilter(){
        return new GrossFilter();
    }

    @Bean
    public FilterRegistrationBean jwtFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(jwtFilter());
        registration.addUrlPatterns("/api/*");
        //registration.addInitParameter("paramName", "paramValue");
        registration.setName("jwtFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }

    @Bean
    public FilterRegistrationBean roleFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(roleFilter());
        registration.addUrlPatterns("/api/*");
        //registration.addInitParameter("paramName", "paramValue");
        registration.setName("roleFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public RoleFilter roleFilter(){
        return new RoleFilter();
    }

}
