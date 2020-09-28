package com.antplatform.admin.biz.config;

import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.biz.infrastructure.shiro.jwt.JWTFilter;
import com.antplatform.admin.biz.infrastructure.shiro.realm.MyRealm;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.service.PermissionService;
import com.antplatform.admin.common.base.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: maoyan
 * @date: 2020/7/8 11:51:34
 * @description:
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Autowired
    private PermissionService permissionService;

    /**
     * 默认premission字符串
     */
    public static final String PERMISSION_STRING = "perms[\"{0}\"]";

    /**
     * Shiro生命周期处理器
     * @return
     */
     @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
//    @DependsOn({"getLifecycleBeanPostProcessor"})
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启shiro aop注解支持,使用代理方式;所以需要开启代码支持;否则@RequiresRoles等注解无法生效
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 注入 securityManager
     * @return
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager getManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(myRealm());

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-
         * StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    /**
     * 先走 filter ，然后 filter 如果检测到请求头存在 token，则用 token 去 login，走 Realm 去验证
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>(Constant.Number.ONE);
        // 设置我们自定义的JWT过滤器
        filterMap.put("jwt", jwtFilter());
        filterMap.put("perms", jwtFilter());
        factoryBean.setFilters(filterMap);
        // 必须设置 SecurityManager
        factoryBean.setSecurityManager(securityManager);
        // 设置无权限时跳转的 url;
        factoryBean.setUnauthorizedUrl("/401");
        /*
         * 自定义url规则 http://shiro.apache.org/web.html#urls-
         * 设置拦截器
         */
        Map<String, String> filterRuleMap = new LinkedHashMap<>(Constant.Number.TWO);
        // 访问401和404页面不通过我们的Filter
        filterRuleMap.put("/401", "anon");
        filterRuleMap.put("/404", "anon");
        filterRuleMap.put("/500", "anon");
        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/swagger-resources/configuration/ui", "anon");
        filterRuleMap.put("/swagger-resources", "anon");
        filterRuleMap.put("/swagger-resources/configuration/security", "anon");
        filterRuleMap.put("/v2/api-docs", "anon");
        filterRuleMap.put("/error", "anon");
        filterRuleMap.put("/webjars/springfox-swagger-ui/**", "anon");
//        QueryWrapper<Resource> wrapper = new QueryWrapper<Resource>();
//        // 获取所有Permission
//        List<Resource> list = resourceService.selectResourceList(wrapper);
        // 获取所有Permission
        PermissionListSpec permissionListSpec = new PermissionListSpec();
        Collection<Permission> list = permissionService.findBySpec(permissionListSpec);

        // 循环Permission的url,逐个添加到filterChainDefinitionMap中。
        // 里面的键就是链接URL,值就是存在什么条件才能访问该链接
        for (Iterator<Permission> it = list.iterator(); it.hasNext();) {
            Permission permission= it.next();
            String pUrl = permission.getUrl();
            String pKey = permission.getKeypoint();
            // 如果不为空值添加到section中
            if (!StringUtils.isEmpty(pUrl) && !StringUtils.isEmpty(pKey)) {
                filterRuleMap.put(pUrl, MessageFormat.format(PERMISSION_STRING, pKey));
            }
        }
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);

        log.info("Shiro拦截器工厂类注入成功");
        return factoryBean;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     * @return
     */
    @Bean
    public MyRealm myRealm(){
        return new MyRealm();
    }

    @Bean
    public JWTFilter jwtFilter(){
        return new JWTFilter();
    }
}