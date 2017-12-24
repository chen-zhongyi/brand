package com.chen.brand.log;

import com.chen.brand.Constant;
import com.chen.brand.mapper.LogMapper;
import com.chen.brand.model.Log;
import com.chen.brand.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
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

    private Logger logger = Logger.getLogger(LogManager.class);

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
            logger.info("======================== params ========================");
            for(int i = 0;i < params.length;++i){
                Object param = params[i];
                String paramName = paramNames[i];
                System.err.println(paramName );
                if(param instanceof HttpServletRequest) continue;
                if(param instanceof Errors) continue;
                if(param instanceof HttpServletResponse) continue;
                paramStr.append(paramName + ": " + new Gson().toJson(param) + ", ");
                logger.info(paramName + ": " + gson.toJson(param));
            }
            logger.info("======================== params ========================");
            log.setParam(paramStr.toString());
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            User user = (User) request.getSession().getAttribute(Constant.SESSION_NAME);
            log.setUser(user == null ? "-1" : user.getUserName());
            logger.info("======================== user ========================");
            logger.info("user: " + gson.toJson(user));
            logger.info("======================== user ========================");
            log.setMethod(jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + "()");
            logger.info("======================== method ========================");
            logger.info(jp.getTarget().getClass().getName() + "." + jp.getSignature().getName() + "()");
            logger.info("======================== method ========================");

            data = jp.proceed();

            Long endTime = System.currentTimeMillis();
            log.setExecutionTime(endTime - startTime);

            logger.info("======================== exeTime ========================");
            logger.info(endTime - startTime + "ms");
            logger.info("======================== exeTime ========================");
            //log.setResult(new Gson().toJson(data));
            log.setType(1L);
            logMapper.insert(log);

        }catch (Throwable e){
            Long endTime = System.currentTimeMillis();
            log.setExecutionTime(endTime - startTime);
            logger.info("======================== exeTime ========================");
            logger.info(endTime - startTime + "ms");
            logger.info("======================== exeTime ========================");
            if(e instanceof java.lang.StackOverflowError) {
                log.setEx(e.toString());
                logger.info("======================== Exception ========================");
                logger.info(e.toString());
                logger.info("======================== Exception ========================");
            }else {
                Writer writer = new StringWriter();
                PrintWriter print = new PrintWriter(writer);
                e.printStackTrace(print);
                log.setEx(writer.toString());
                logger.info("======================== Exception ========================");
                logger.info(writer.toString());
                logger.info("======================== Exception ========================");
                writer.close();
                print.close();
            }
            log.setType(0L);
            logMapper.insert(log);

            throw e;
        }
        logger.info("");
        return data;
    }
}
