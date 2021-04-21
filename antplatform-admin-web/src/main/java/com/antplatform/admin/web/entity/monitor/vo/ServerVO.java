package com.antplatform.admin.web.entity.monitor.vo;

import lombok.Data;

import java.util.Map;

/**
 * @author: maoyan
 * @date: 2021/3/29 20:49:56
 * @description:
 */
@Data
public class ServerVO {
    private Map<String,Object> system;
    private Map<String,Object> cpu;
    private Map<String,Object> jvm;
    private Map<String,Object> memory;
    private Map<String,Object> swap;
    private Map<String,Object> disk;
    private String time;
}
