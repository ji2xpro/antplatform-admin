package com.antplatform.admin.biz.infrastructure.shiro.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: maoyan
 * @date: 2021/2/8 11:21:59
 * @description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "token")
public class JwtProperties {
    /**
     * token过期时间，单位分钟
     */
    Integer tokenExpireTime;

    /**
     * 更新令牌时间，单位分钟
     */
    Integer refreshCheckTime;

    /**
     * Shiro缓存有效期，单位分钟
     */
    Integer shiroCacheExpireTime;

    /**
     * token加密密钥
     */
    String secretKey;

    Map<String,Long> expireTime;
}
