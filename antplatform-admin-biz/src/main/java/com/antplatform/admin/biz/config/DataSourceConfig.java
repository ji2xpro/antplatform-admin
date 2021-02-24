package com.antplatform.admin.biz.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.antplatform.admin.biz.infrastructure.tkmybatis.PageInterceptor;
import com.antplatform.admin.biz.infrastructure.tkmybatis.ext.TKMybatisConfiguration;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author: maoyan
 * @date: 2020/9/1 16:21:34
 * @description:
 */
@Configuration
@MapperScan(basePackages = "com.antplatform.admin.biz.model", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {
    /**
     * 将自定义的 Druid数据源添加到容器中，不再让 Spring Boot 自动创建
     * 绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource从而让它们生效
     *
     * @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中
     * 前缀为 spring.datasource的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中
     */
    @Bean("master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("master") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // 设置查找器
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
        sessionFactory.setTypeAliasesPackage("com.antplatform.admin.biz.model");

        //分页插件
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("dialectClass", "com.antplatform.admin.biz.infrastructure.dialect.MySQLDialect");

        Interceptor interceptor = new PageInterceptor();
        interceptor.setProperties(properties);
        sessionFactory.setPlugins(new Interceptor[]{interceptor});

        return sessionFactory.getObject();
    }

    @Bean("transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("master") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
