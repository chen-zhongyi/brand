package com.chen.brand.util;

import com.chen.brand.Constant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTUtil {

    public static Claims parse(String jwtStr){
        return Jwts.parser().setSigningKey(Constant.JWT_SECRET).parseClaimsJws(jwtStr).getBody();
    }

}
