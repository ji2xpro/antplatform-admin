package com.antplatform.admin.biz.infrastructure.shiro.jwt;

import com.antplatform.admin.api.request.PermissionListSpec;
import com.antplatform.admin.biz.infrastructure.shiro.ShiroFilterProperties;
import com.antplatform.admin.biz.infrastructure.shiro.cache.ShiroCacheManager;
import com.antplatform.admin.biz.infrastructure.shiro.credentials.CredentialsMatcher;
import com.antplatform.admin.biz.infrastructure.shiro.credentials.RetryLimitCredentialsMatcher;
import com.antplatform.admin.biz.infrastructure.shiro.realm.UserRealm;
import com.antplatform.admin.biz.infrastructure.shiro.realm.JwtRealm;
import com.antplatform.admin.biz.model.Permission;
import com.antplatform.admin.biz.service.PermissionService;
import com.antplatform.admin.common.base.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ShiroConfig:shiro 配置类,配置哪些拦截,哪些不拦截,哪些授权等等各种配置都在这里
 *
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
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LifecycleBeanPostProcessor.class)
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启shiro aop注解支持,使用代理方式;所以需要开启代码支持;否则@RequiresRoles等注解无法生效
     *
     * @param securityManager
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(AuthorizationAttributeSourceAdvisor.class)
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 注入安全管理器 securityManager
     *
     * @return
     */
    @Bean("securityManager")
    @ConditionalOnMissingBean(SecurityManager.class)
    public DefaultWebSecurityManager securityManager(ShiroCacheManager shiroCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 1.Authenticator
        securityManager.setAuthenticator(authenticator());

        // 2.注入域 自定义Realm
        List<Realm> realms = new ArrayList<Realm>(16);
        realms.add(jwtRealm());
        realms.add(userRealm());
        securityManager.setRealms(realms);

         // 3.关闭shiro自带的session，详情见文档 http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        securityManager.setCacheManager(shiroCacheManager);

        return securityManager;
    }

    /**
     * 注入安全过滤器
     * 先走 filter, 然后 filter 如果检测到请求头存在 token，则用 token 去 login，走 Realm 去验证
     *
     * 如果没有此name,将会找不到shiroFilter的Bean
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    @ConditionalOnMissingBean(ShiroFilter.class)
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new LinkedHashMap<>(Constant.Number.ONE);
        // 设置我们自定义的JWT过滤器 **自定义过滤器不能用@bean注入，由于spring的机制，注入的话定义的anon的资源也要全部走一遍自定义过滤器，也就是说走一遍anon过滤器，还会走自定义过滤器，也就是走两遍，那我们anon就相当于失效了，所以这里用new**
        filterMap.put("jwt",  jwtFilter());
        filterMap.put("logout", new SystemLogoutFilter());
//        filterMap.put("perms", new JwtFilter());
        shiroFilter.setFilters(filterMap);
        // 注入管理器 必须设置 SecurityManager
        shiroFilter.setSecurityManager(securityManager);
        // 设置无权限时跳转的 url;
        shiroFilter.setUnauthorizedUrl("/401");
        /*
         * 自定义url规则 http://shiro.apache.org/web.html#urls-
         * 设置拦截器
         * 配置不会被拦截的链接 顺序判断
         */
        Map<String, String> filterRuleMap = new LinkedHashMap<>(Constant.Number.TWO);
        // 访问401和404页面不通过我们的Filter
//        filterRuleMap.put("/401", "anon");
//        filterRuleMap.put("/404", "anon");
//        filterRuleMap.put("/500", "anon");
//        filterRuleMap.put("/swagger-ui.html", "anon");
//        filterRuleMap.put("/swagger-resources/configuration/ui", "anon");
//        filterRuleMap.put("/swagger-resources", "anon");
//        filterRuleMap.put("/swagger-resources/configuration/security", "anon");
//        filterRuleMap.put("/v2/api-docs", "anon");
//        filterRuleMap.put("/error", "anon");
//        filterRuleMap.put("/webjars/springfox-swagger-ui/**", "anon");

        List<Map<String, String>> perms = this.getShiroFilterProperties().getPerms();
        perms.forEach(perm -> filterRuleMap.put(perm.get("key"), perm.get("value")));
        // 获取所有Permission
        PermissionListSpec permissionListSpec = new PermissionListSpec();
        Collection<Permission> list = permissionService.findBySpec(permissionListSpec);
        // 循环Permission的url,逐个添加到filterChainDefinitionMap中。
        // 里面的键就是链接URL,值就是存在什么条件才能访问该链接
        for (Iterator<Permission> it = list.iterator(); it.hasNext(); ) {
            Permission permission = it.next();
            String pUrl = permission.getUrl();
            String pKey = permission.getKeypoint();
            // 如果不为空值添加到section中
            if (!StringUtils.isEmpty(pUrl) && !StringUtils.isEmpty(pKey)) {
                filterRuleMap.put(pUrl, MessageFormat.format(PERMISSION_STRING, pKey));
            }
        }
        // 访问 /unauthorized/** 不通过JWTFilter
//        filterRuleMap.put("/**/unauthorized/**", "anon");
//        filterRuleMap.put("/unauthorized/**", "anon");
//        filterRuleMap.put("/**/timeout", "anon");
        // 所有请求通过我们自己的JWT Filter，这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterRuleMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);

        log.info("Shiro拦截器工厂类注入成功");
        return shiroFilter;
    }

    /**
     * 用于JWT token认证的realm
     *
     * @return
     */
    @Bean
    public JwtRealm jwtRealm() {
        JwtRealm jwtRealm = new JwtRealm();
        // 设置认证器 RetryLimitCredentialsMatcher
        jwtRealm.setCredentialsMatcher(credentialsMatcher());
        return jwtRealm;
    }

    /**
     * 用于用户名密码登录时认证的realm
     *
     * @return
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        // 设置认证器 RetryLimitCredentialsMatcher
        userRealm.setCredentialsMatcher(retryLimitCredentialsMatcher());
        return userRealm;
    }

    /**
     * 注入自定义认证器
     * @return
     */
    @Bean
    public RetryLimitCredentialsMatcher retryLimitCredentialsMatcher() {
        return new RetryLimitCredentialsMatcher();
    }

    /**
     * 注入自定义认证器
     * @return
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    /**
     * 配置 ModularRealmAuthenticator
     */
    @Bean
    public ModularRealmAuthenticator authenticator() {
        ModularRealmAuthenticator authenticator = new MultiRealmAuthenticator();
        // 设置多个Realm的认证策略，一个成功即跳过其它的，默认 AtLeastOneSuccessfulStrategy
        AuthenticationStrategy strategy = new FirstSuccessfulStrategy();
        authenticator.setAuthenticationStrategy(strategy);
        return authenticator;
    }

//    @Bean
//    public ShiroCacheManager shiroCacheManager(){
//        return new ShiroCacheManager();
//    }

    @Bean
    public ShiroFilterProperties getShiroFilterProperties(){
        return new ShiroFilterProperties();
    }

    /**
     * 不向 Spring容器中注册 JwtFilter Bean，防止 Spring 将 JwtFilter 注册为全局过滤器，全局过滤器会对所有请求进行拦截
     * 另一种简单做法是：直接去掉 jwtFilter()上的 @Bean 注解，使用new注入
     */
    @Bean
    public FilterRegistrationBean<Filter> registration(JwtFilter filter) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

}