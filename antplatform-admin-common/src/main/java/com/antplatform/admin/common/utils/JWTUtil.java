package com.antplatform.admin.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Map;

/**
 * @author: maoyan
 * @date: 2020/9/25 17:48:30
 * @description:
 */
@Component
public class JWTUtil {

    private static Map<String, Long> expireTime;

    /**
     * 过期时间
     */
    @Value("#{${system.expireTime}}")
    private Map<String, Long> expireTimeYml;

    /**
     * 将接收到的值赋值给静态变量
     */
    @PostConstruct
    public void getValue(){
        expireTime = this.expireTimeYml;
    }

    /**
     * 生成签名,expireTime后过期
     *
     * @param userAccount
     *            用户账号
     * @param secret
     *            用户的密码
     * @param expTimeType
     *            (1、web 2、 app) 失效时间类型
     * @return 加密的token
     */
    public static String sign(String userAccount, String secret, String expTimeType) {
        try {
            long expTime = expireTime.get(expTimeType);
            Date date = new Date(System.currentTimeMillis() + expTime);
            Algorithm algorithm = Algorithm.HMAC256(userAccount + secret);
            // 附带username信息
            return JWT.create()
                    .withClaim("useraccount", userAccount)
                    // 到期时间
                    .withExpiresAt(date)
                    // 创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验token是否正确
     *
     * @param token
     *            密钥
     * @param secret
     *            用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String userAccount, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(userAccount + secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("useraccount", userAccount).build();
            verifier.verify(token);
            return true;
        } catch (TokenExpiredException expiredException) {
            throw expiredException;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUserAccount(String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("useraccount").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的过期时间，判断是否重新生成token
     *
     * @return token中包含的过期时间
     */
    public static Date getExpiresAt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getExpiresAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
