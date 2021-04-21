package com.antplatform.admin.web.action.monitor;

import com.antplatform.admin.api.dto.CacheDTO;
import com.antplatform.admin.common.dto.Response;
import com.antplatform.admin.common.result.AjaxResult;
import com.antplatform.admin.common.utils.TransformUtils;
import com.antplatform.admin.web.biz.monitor.CacheBiz;
import com.antplatform.admin.web.entity.monitor.vo.CacheVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: maoyan
 * @date: 2020/9/15 19:39:31
 * @description:
 */
@RestController
@RequestMapping("/cache")
@Api(value = "CacheAction|缓存监控的前端控制器")
public class CacheAction {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CacheBiz cacheBiz;

    @GetMapping(value = "/monitor")
    @RequiresRoles("admin")
    @ApiOperation(value = "查询缓存监控数据", notes = "展示缓存监控数据信息")
    @ApiImplicitParam(paramType = "query", name = "parentId", value = "父级ID", required = false, dataType = "Integer")
    public AjaxResult queryServerInfo() {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

        Map<String, Object> result = new HashMap<>(3);
        result.put("info", info);
        result.put("dbSize", dbSize);

        List<Map<String, String>> pieList = new ArrayList<>();
        commandStats.stringPropertyNames().forEach(key -> {
            Map<String, String> data = new HashMap<>(2);
            String property = commandStats.getProperty(key);
            data.put("name", StringUtils.removeStart(key, "cmdstat_"));
            data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
            pieList.add(data);
        });
        result.put("commandStats", pieList);

        return AjaxResult.createSuccessResult(result);
    }

    @GetMapping(value = "/monitor1")
    @RequiresRoles("admin")
    @ApiOperation(value = "查询缓存监控数据", notes = "展示缓存监控数据信息")
    @ApiImplicitParam(paramType = "query", name = "parentId", value = "父级ID", required = false, dataType = "Integer")
    public AjaxResult<CacheVO> queryServerInfo1() {
        Response<CacheDTO> response = cacheBiz.queryCacheInfo();

        if (response.isSuccess()) {
            CacheDTO cacheDTO = response.getData();

            CacheVO cacheVO = TransformUtils.simpleTransform(cacheDTO, CacheVO.class);

            return AjaxResult.createSuccessResult(cacheVO);
        }
        return AjaxResult.createFailedResult(response.getCode(), response.getMsg());
    }
}
