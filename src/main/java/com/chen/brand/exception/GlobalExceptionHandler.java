package com.chen.brand.exception;

import com.chen.brand.Constant;
import org.apache.log4j.Logger;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, String> defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception{
        Map<String, String> res = new HashMap<>();
        res.put("code", Constant.FAIL);
        res.put("msg", "请求:" + request.getRequestURL() + ", 服务器出错啦！" );
        e.printStackTrace();
        /*log.error(e);
        StackTraceElement[] ste = e.getStackTrace();
        for(StackTraceElement s : ste) {
            //System.out.println("chen -- " + s);
            log.error("   at " + s);
        }*/
        return res;
    }

    @ExceptionHandler( value = SQLException.class)
    @ResponseBody
    public Map<String, String> sqlExceptionHandler(HttpServletRequest request, SQLException e) throws Exception {
        Map<String, String> res = new HashMap<>();
        res.put("code", Constant.FAIL);
        res.put("msg", "请求:" + request.getRequestURL() + ", 数据库出错啦！");
        e.printStackTrace();
        /*log.error(e);
        StackTraceElement[] ste = e.getStackTrace();
        for(StackTraceElement s : ste) {
            //System.out.println("chen -- " + s);
            log.error("   at " + s);
        }*/
        return res;
    }

    @ExceptionHandler( value = NoHandlerFoundException.class)
    @ResponseBody
    public Map<String, String> noFoundExceptionHandler(HttpServletRequest request, NoHandlerFoundException e) throws Exception {
        Map<String, String> res = new HashMap<>();
        res.put("code", Constant.FAIL);
        res.put("msg", "请求:" + request.getRequestURL() + ", api未定义！");
        e.printStackTrace();
        /*log.error(e);
        StackTraceElement[] ste = e.getStackTrace();
        for(StackTraceElement s : ste) {
            //System.out.println("chen -- " + s);
            log.error("   at " + s);
        }*/
        return res;
    }

    @ExceptionHandler( value = MissingServletRequestParameterException.class)
    @ResponseBody
    public Map<String, String> missServletRequestParamExceptionHandler(HttpServletRequest request, MissingServletRequestParameterException e) throws Exception {
        Map<String, String> res = new HashMap<>();
        res.put("code", Constant.FAIL);
        res.put("msg", "请求:" + request.getRequestURL() + ", 请求参数缺失！");
        e.printStackTrace();
        /*log.error(e);
        StackTraceElement[] ste = e.getStackTrace();
        for(StackTraceElement s : ste) {
            //System.out.println("chen -- " + s);
            log.error("   at " + s);
        }*/
        return res;
    }
}
