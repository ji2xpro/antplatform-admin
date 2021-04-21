package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.BaseDTO;
import lombok.Data;

import java.util.Map;

/**
 * @author: maoyan
 * @date: 2021/3/29 20:53:31
 * @description:
 */
@Data
public class ServerDTO extends BaseDTO {
    private Map<String,Object> system;
    private Map<String,Object> cpu;
    private Map<String,Object> jvm;
    private Map<String,Object> memory;
    private Map<String,Object> swap;
    private Map<String,Object> disk;
    private String time;
}
