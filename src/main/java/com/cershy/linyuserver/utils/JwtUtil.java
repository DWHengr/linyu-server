package com.cershy.linyuserver.utils;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

/**
 * @author: dwh
 **/
@Component
public class JwtUtil implements Serializable {
    private static final long serialVersionUID = -5625635588908941275L;

    // 令牌秘钥
    private static String secret = "linyu-E7Ymu64s";

    // 令牌有效期
    private static int days = 30;

    /**
     * 获取token
     *
     * @param claims
     * @return
     */
    public static String createToken(Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant expireTime = now.plus(days, ChronoUnit.DAYS);
        return Jwts.builder()
                .setIssuer("cershy")
                .addClaims(claims)
                .setExpiration(Date.from(expireTime))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }


    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static Claims parseToken(String token) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(secret);
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        return body;
    }

}
