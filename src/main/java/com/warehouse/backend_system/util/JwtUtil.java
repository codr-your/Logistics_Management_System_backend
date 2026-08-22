package com.warehouse.backend_system.util;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtil {
    // 密钥，项目自己修改
    private static final String SECRET_KEY = "warehouse2026secretkeyabc123456";
    // token过期时间 2小时 单位毫秒
    private static final long EXPIRE_TIME = 2 * 60 * 60 * 1000;

    /**
     * 生成token，传入用户id、用户名
     */
    public static String generateToken(Long userId, String username){
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .setId(userId.toString())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY);
        return builder.compact();
    }

    /**
     * 解析token，获取用户名
     */
    public static String getUsername(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            return claims.getSubject();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 校验token是否合法有效
     */
    public static boolean validateToken(String token){
        String username = getUsername(token);
        return username != null;
    }
}