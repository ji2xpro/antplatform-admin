package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.BaseDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: maoyan
 * @date: 2021/3/31 17:37:52
 * @description:
 */
@Data
public class CacheDTO extends BaseDTO {
    private Properties info;
    private Object dbSize;
    private List<Map<String, String>> commandStats;
}
