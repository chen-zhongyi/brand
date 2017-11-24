package com.chen.brand.controller;


import com.chen.brand.util.JWTUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BaseController {

    protected Map<String, Object> createResponse(String code, String msg, Object data){
        Map<String, Object > response = new HashMap<>();
        response.put("code", code);
        response.put("msg", msg);
        response.put("data", data);
        return response;
    }

    protected Map<String, String> createErrors(Errors errors){
        Map<String, String> e = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            e.put(error.getField(), error.getDefaultMessage());
        }
        return e;
    }

    public static String getMd5String(String str){
        return DigestUtils.md5Hex(str);
    }

    public static String getOtherStr(){
        String[] ch = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
        StringBuffer sb = new StringBuffer("");
        Random random = new Random(System.currentTimeMillis());
        for(int i = 0;i < 12;++i){
            sb.append(ch[random.nextInt(ch.length)]);
        }
        return sb.toString();
    }

    public static void main(String[] args){
        System.out.println(getMd5String("123456"));
        char temp = 'A';
        for(int i = 0;i < 26;++i){
            System.out.print("\"" + temp + "\", ");
            temp++;
        }
        for(int i = 0;i < 10;++i){
            String otherStr = getOtherStr();
            System.out.println(otherStr);
            System.out.println(getMd5String("123qwer" + otherStr));
            try{
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public Long getUserId(String jwt){
        return Long.valueOf(JWTUtil.parse(jwt).get("id", Integer.class));
    }

    public Long getUserType(String jwt){
        return Long.valueOf(JWTUtil.parse(jwt).get("type", Integer.class));
    }

    public String getUserAreaCode(String jwt){
        return JWTUtil.parse(jwt).get("areaCode", String.class);
    }

    public char[] illegalChar = {'-', '&', ' ', '\'', '{', '}', '/', '\\', '.'};

    public boolean checkUserName(String userName){
        for(int i = 0;i < userName.length();++i){
            for(char c : illegalChar){
                if(c == userName.charAt(i)) return false;
            }
        }
        return true;
    }

    public Object[] getApi(String url){
        Object[] res = {"", null};
        String[] temp = new String[10];
        int len = 0;
        for(String str : url.split("/")){
            if(str.equals(""))  continue;
            //System.err.println(str);
            boolean flag = false;
            Long ans = 0L;
            for(int i = 0;i < str.length();++i){
                if(str.charAt(i) < '0' || str.charAt(i) > '9'){
                    flag = true;
                    break;
                }
                ans = ans * 10 + (str.charAt(i) - '0');
            }
            if(flag){
                if(len == 10)   return res;
                temp[len++] = str;
            }
            if(! flag){
                res[1] = ans;
            }
        }
        String str = "";
        for(int i = 0;i < len;++i){
            str += "/" + temp[i];
        }
        res[0] = str;
        return res;
    }
}
