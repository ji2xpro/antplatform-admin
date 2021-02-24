package com.antplatform.admin.biz.infrastructure.shiro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author: maoyan
 * @date: 2021/2/7 19:56:24
 * @description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "permission-config")
public class ShiroFilterProperties {
    List<Map<String, String>> perms;
}
