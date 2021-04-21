package com.antplatform.admin.web.entity.monitor.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: maoyan
 * @date: 2021/3/31 17:46:33
 * @description:
 */
@Data
public class CacheVO {
    private Properties info;
    private Object dbSize;
    private List<Map<String, String>> commandStats;
}
