package com.chen.brand.log;

import com.chen.brand.Constant;
import com.chen.brand.mapper.LogMapper;
import com.chen.brand.model.Log;
import com.chen.brand.model.User;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Timestamp;

@Aspect
public class LogManager {

    @Autowired
    private LogMapper logMapper;

    @Pointcut(value = "execution(* com.chen.brand.controller.*.*(..)) " +
            "&& (! within(com.chen.brand.controller.BaseController))")
    public void controllerLog(){}

    @Around("controllerLog()")
    public Object around(ProceedingJoinPoint jp) throws Throwable{
        Log log = new Log();
        Long startTime = System.currentTimeMillis();
        log.setCreateTime(new Timestamp(startTime));
        Object data = null;
        try {
            StringBuffer paramStr = new StringBuffer("");
            Object[] params = jp.getArgs();
            String[] paramNames = ((MethodSignature) jp.getSignature()).getParameterNames();
            for(int i = 0;i < params.length;++i){
                Object param = params[i];
                String paramName = paramNames[i];
                System.err.println(paramName );
                if(param instanceof HttpServletRequest) continue;
                if(param instanceof Errors) continue;
                if(param instanceof HttpServletResponse) continue;
                paramStr.append(paramName + ": " + new Gson().toJson(param) + ", ");
            }
            log.setParam(paramStr.toString());
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            User user = (User) request.getSession().getAttribute(Constant.SESSION_NAME);
            log.setUser(user == null ? "-1" : user.getUserName());
            log.setMethod(jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + "()");

            data = jp.proceed();

            Long endTime = System.currentTimeMillis();
            log.setExecutionTime(endTime - startTime);
            //log.setResult(new Gson().toJson(data));
            log.setType(1L);
            logMapper.insert(log);

        }catch (Throwable e){
            Long endTime = System.currentTimeMillis();
            log.setExecutionTime(endTime - startTime);
            if(e instanceof java.lang.StackOverflowError) {
                log.setEx(e.toString());
            }else {
                Writer writer = new StringWriter();
                PrintWriter print = new PrintWriter(writer);
                e.printStackTrace(print);
                log.setEx(writer.toString());
                writer.close();
                print.close();
            }
            log.setType(0L);
            logMapper.insert(log);

            throw e;
        }
        return data;
    }
}
