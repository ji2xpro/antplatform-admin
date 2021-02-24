package com.antplatform.admin.biz.infrastructure.shiro.jwt;

import com.antplatform.admin.common.base.Constants.SecurityConstant;
import com.antplatform.admin.common.utils.RedisUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Map;

/**
 * JwtUtil:用来进行签名和效验Token
 *
 * @author: maoyan
 * @date: 2020/9/25 17:48:30
 * @description:
 */
@Slf4j
@Component
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

//    @Autowired
//    private JedisUtils jedisUtils;

    @Autowired
    private static JwtUtil jwtUtil;

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
    public void getValue() {
        expireTime = this.expireTimeYml;
        jwtUtil = this;
        jwtUtil.jwtProperties = this.jwtProperties;
//        jwtUtil.jedisUtils = this.jedisUtils;
    }

    /**
     * 生成 token 签名
     *
     * @param account
     * @param secret
     * @param expTimeType
     * @param millTimes
     * @return
     */
    public static String genToken(String account, String secret, String expTimeType, long millTimes) {
        log.info(String.format("为账户%s颁发新的令牌", account));
        String currentTimeMillis = String.valueOf(millTimes);
        String token = JwtUtil.sign(account, secret, expTimeType, currentTimeMillis);
        //更新RefreshToken缓存的时间戳
        String refreshTokenKey = SecurityConstant.PREFIX_SHIRO_REFRESH_TOKEN + account;
        RedisUtil.set(refreshTokenKey, currentTimeMillis, jwtUtil.jwtProperties.getTokenExpireTime() * 60);

        return token;
    }

    /**
     * 生成签名,expireTime后过期
     *
     * @param account     用户账号
     * @param secret      用户的密码
     * @param expTimeType (1、web 2、 app) 失效时间类型
     * @return 加密的token
     */
    private static String sign(String account, String secret, String expTimeType, String currentTimeMillis) {
        try {
            // 过期时间以毫秒为单位
            long expTime = expireTime.get(expTimeType);
//            long expTime = jwtUtil.jwtProperties.expireTime.get(expTimeType);

            Date date = new Date(System.currentTimeMillis() + expTime);

            Algorithm algorithm = Algorithm.HMAC256(account + secret);
            // 附带username信息
            return JWT.create()
                    .withClaim(SecurityConstant.ACCOUNT, account)
                    .withClaim(SecurityConstant.CURRENT_TIME_MILLIS, currentTimeMillis)
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
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String account, String secret) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(account + secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(SecurityConstant.ACCOUNT, account).build();
            //效验TOKEN
            verifier.verify(token);
            log.info("登录验证成功!");
            return true;
        } catch (JWTVerificationException jwtVerificationException) {
            throw jwtVerificationException;
        } catch (Exception exception) {
            log.error("JwtUtil登录验证失败!");
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getClaim(String token, String... claim) {
        String name = SecurityConstant.ACCOUNT;
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        if (claim != null && claim.length > 0) {
            name = claim[0];
        }
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(name).asString();
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
