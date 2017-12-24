package com.chen.brand.controller;

import com.chen.brand.Constant;
import com.chen.brand.Enum.RoleType;
import com.chen.brand.Enum.UserType;
import com.chen.brand.http.request.LoginResgister.Login;
import com.chen.brand.http.request.LoginResgister.Register;
import com.chen.brand.model.User;
import com.chen.brand.model.UserCookie;
import com.chen.brand.service.RoleService;
import com.chen.brand.service.SampleService;
import com.chen.brand.service.UserCookieService;
import com.chen.brand.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping( value = "")
@Api( value = "api --- loginRegisterController", description = "登陆、注册接口")
public class LoginRegisterController extends BaseController{

    private static final int COOKIE_TIME = 60 * 60 * 24 * 30;

    @Autowired
    private UserService userService;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserCookieService cookieService;

    @ApiOperation(value = "注册用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", required = true, dataType = "Register", paramType = "body")
    })
    @PostMapping("/register")
    public Map<String, Object> register(@ApiIgnore HttpServletResponse response,
                                        @ApiIgnore HttpServletRequest httpRequest,
                                        @RequestBody @Valid Register request,
                                        @ApiIgnore Errors errors){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(userService.isExistUserName(request.getUserName()) == true){
            Map<String, Object> data = new HashMap<>();
            data.put("userName", "用户名已存在");
            return createResponse(Constant.FAIL, "验证参数失败", data);
        }
        if(checkUserName(request.getUserName()) == false){
            return createResponse(Constant.FAIL, "用户名包含非法字符" + Arrays.toString(illegalChar), null);
        }
        User user = new User();
        user.setUserName(request.getUserName());
        user.setRealName(request.getRealName());
        user.setOtherStr(getOtherStr());
        user.setPwd(getMd5String(request.getPwd() + user.getOtherStr()));
        user.setRole(RoleType.USER.code());
        user.setType(UserType.USER.getCode());
        user.setStatus(true);
        user.setCreateAt(new Timestamp(System.currentTimeMillis()));
        user.setCreateBy(-1L);
        user.setRight(roleService.findByCode(RoleType.USER.code()).getRight());
        user.setLoginIp(getIpAddress(httpRequest));
        Long id = userService.insert(user);
        Map<String, Object> data = userService.findOne(id).buildResponse();
        data.put("sample", sampleService.findByUserId(id));

        httpRequest.getSession().setAttribute(Constant.SESSION_NAME, user);
        createCookie(user, response);

        /*String jwt = createJwt(user);
        Cookie cookie = new Cookie(Constant.COOKIE_NAME, jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(2 * 60 * 60 * 24);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);*/

        return createResponse(Constant.SUCCESS, "成功", data);
    }

    @ApiOperation("是否登录")
    @PostMapping("/isLogin")
    public Map<String, Object> isLogin(@ApiIgnore HttpServletRequest request){
        /*Cookie[] cookies = request.getCookies();
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
        if(jwt.equals("")){
            return createResponse(Constant.FAIL, "未登录", null);
        }
        Long userId = -1L;
        try{
            Jwts.parser().setSigningKey(Constant.JWT_SECRET).parseClaimsJws(jwt);
            userId = Long.valueOf(Jwts.parser().setSigningKey(Constant.JWT_SECRET).parseClaimsJws(jwt).getBody().get("id", java.lang.Integer.class));

        }catch (SignatureException e){
            return createResponse(Constant.FAIL, "未登录", null);
        }*/

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.SESSION_NAME);
        if(user == null){
            String userCookie = findCookie(request, Constant.COOKIE_NAME);
            boolean isEnable = false;
            if( ! userCookie.equals(""))  {
                String[] userCookies = userCookie.split("-");
                if(userCookies.length == 2){
                    Long userId;
                    try {
                        userId = Long.valueOf(userCookies[0]);
                    }catch(Exception e){
                        return createResponse(Constant.SUCCESS, "未登录", null);
                    }
                    user = userService.findOne(userId);
                    UserCookie c = cookieService.find(userId, userCookie, getIpAddress(request));
                    if(user != null && c != null && user.getId().longValue() == c.getUserId().longValue()
                            && getMd5String(user.getPwd() + "," + user.getOtherStr()).equals(userCookies[1])){
                        session.setAttribute(Constant.SESSION_NAME, user);
                        isEnable = true;
                    }
                }
            }
            if(! isEnable){
                return createResponse(Constant.SUCCESS, "未登录", null);
            }
        }
        Map<String, Object> data = user.buildResponse();
        data.put("sample", sampleService.findByUserId(user.getId()));
        return createResponse(Constant.SUCCESS, "成功", data);
    }

    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request", value = "请求体", required = true, dataType = "Login", paramType = "body")
    })
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody @Valid Login request,
                                     @ApiIgnore Errors errors,
                                     @ApiIgnore HttpServletResponse response,
                                     @ApiIgnore HttpServletRequest httpRequest){
        if(errors.hasErrors()){
            return createResponse(Constant.FAIL, "参数验证失败", createErrors(errors));
        }
        if(userService.isExistUserName(request.getUserName()) == false){
            Map<String, Object> data = new HashMap<>();
            data.put("userName", "用户名不存在或者密码不匹配");
            return createResponse(Constant.FAIL, "验证参数失败", data);
        }
        User user = userService.findByUserName(request.getUserName());
        if(userService.checkPwd(request.getUserName(), getMd5String(request.getPwd() + user.getOtherStr())) == false){
            Map<String, Object> data = new HashMap<>();
            data.put("userName", "用户名不存在或者密码不匹配");
            return createResponse(Constant.FAIL, "验证参数失败", data);
        }
        user.setLoginIp(getIpAddress(httpRequest));
        user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
        user.setCount(user.getCount() + 1);
        userService.update(user);
        httpRequest.getSession().setAttribute(Constant.SESSION_NAME, user);
        createCookie(user, response);

        /*String jwt = createJwt(user);
        Cookie cookie = new Cookie(Constant.COOKIE_NAME, jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(2 * 60 * 60 * 24);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);
        //response.addHeader("Set-Cookie", "jwt=" + jwt + ";expires=Sat,03 May 2025 17:44:22 GMT;HttpOnly");
        */
        Map<String, Object> data = userService.findOne(user.getId()).buildResponse();
        data.put("sample", sampleService.findByUserId(user.getId()));
        return createResponse(Constant.SUCCESS, "成功", data);
    }

    @ApiOperation(value = "登出")
    @DeleteMapping("/logout")
    public Map<String, Object> logout(@ApiIgnore HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(Constant.SESSION_NAME);
        if(user != null){
            System.out.println(user.getId());
            System.out.println(findCookie(request, Constant.COOKIE_NAME));
            System.out.println(getIpAddress(request));
            cookieService.delete(user.getId(), findCookie(request, Constant.COOKIE_NAME), getIpAddress(request));
            request.getSession().removeAttribute(Constant.SESSION_NAME);
        }
        return createResponse(Constant.SUCCESS, "成功", null);
    }

    private String findCookie(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        System.out.println("cookie : " + new GsonBuilder().setPrettyPrinting().create().toJson(cookies));
        String userCookie = "";
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    userCookie = cookie.getValue();
                    break;
                }
            }
        }
        return userCookie;
    }

    private void createCookie(User user, HttpServletResponse response){
        String userCookie = user.getId() + "-" + getMd5String( user.getPwd() + "," + user.getOtherStr());
        System.err.println("userCookie = " + userCookie);
        Cookie cookie = new Cookie(Constant.COOKIE_NAME, userCookie);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_TIME);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);
        UserCookie c = cookieService.find(user.getId(), userCookie, user.getLoginIp());
        if(c == null) {
            c = new UserCookie(user.getId(), userCookie, user.getLoginIp());
            cookieService.insert(c);
        }
    }

    private String createJwt(User user){
        Map<String, Object> data = new HashMap<>();
        data.put("areaCode", user.getAreaCode());
        data.put("id", user.getId());
        data.put("role", user.getRole());
        data.put("type", user.getType());
        data.put("right", user.getRight());
        return Jwts.builder()
                .setIssuer("chenzhongyi")
                .setSubject(user.getUserName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 2 * 60 * 1000))
                .setClaims(data)
                .signWith(SignatureAlgorithm.HS256, Constant.JWT_SECRET)
                .compact();
    }

}
