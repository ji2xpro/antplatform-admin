package com.antplatform.admin.service;

import com.antplatform.admin.api.CacheMgtApi;
import com.antplatform.admin.api.ServerMgtApi;
import com.antplatform.admin.api.dto.CacheDTO;
import com.antplatform.admin.api.dto.ServerDTO;
import com.antplatform.admin.common.base.Constant;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.dto.Responses;
import com.antplatform.admin.common.utils.DataUtil;
import com.antplatform.admin.common.utils.FileUtil;
import com.antplatform.admin.common.utils.IpUtil;
import com.antplatform.admin.common.utils.MathUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: maoyan
 * @date: 2021/3/5 17:50:29
 * @description:
 */
@Service("CacheMgtApi")
public class CacheMgtPortService implements CacheMgtApi {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 查询缓存信息
     *
     * @return
     */
    @Override
    public Response<CacheDTO> findBySpec() {
        CacheDTO cacheDTO = new CacheDTO();

        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

//        Map<String, Object> result = new HashMap<>(3);
//        result.put("info", info);
//        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
//        result.put("commandStats", pieList);

        cacheDTO.setInfo(info);
        cacheDTO.setDbSize(dbSize);
        cacheDTO.setCommandStats(pieList);

        return Responses.of(cacheDTO);
    }
}
