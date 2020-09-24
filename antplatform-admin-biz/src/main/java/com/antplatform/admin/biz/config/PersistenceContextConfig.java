package com.antplatform.admin.biz.config;

import com.antplatform.admin.biz.infrastructure.tkmybatis.PageInterceptor;
import com.antplatform.admin.biz.infrastructure.tkmybatis.ext.TKMybatisConfiguration;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author: maoyan
 * @date: 2020/9/1 16:21:34
 * @description:
 */
@Configuration
public class PersistenceContextConfig {

    @Bean("master")
    @Primary
    // application.properteis中对应属性的前缀
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();

//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(db1Classname);
//        druidDataSource.setUrl(db1Url);
//        druidDataSource.setName(db1Username);
//        druidDataSource.setPassword(db1Password);
//        druidDataSource.setName(db1Name);
//        return druidDataSource;


//        return initCustomDataSources("db1");

//        return DruidDataSourceBuilder.create().build();


    }

//    @Bean(value = "merchant-master", initMethod = "init", destroyMethod = "close")
//    public GroupDataSource groupDataSource() {
//        return new GroupDataSource("AntPlatform");
//    }

//    @Bean
//    public DataSourceTransactionManager transactionManager(@Qualifier("merchant-master") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean
//    public ZebraMapperScannerConfigurer scannerConfigurer() {
//        ZebraMapperScannerConfigurer scannerConfigurer = new ZebraMapperScannerConfigurer();
//        scannerConfigurer.setBasePackage("com.maoyan.show.member.mapper");
//        scannerConfigurer.setSqlSessionFactoryBeanName("zebraSqlSessionFactory");
//        return scannerConfigurer;
//    }

//    @Bean
//    public TKMybatisConfiguration mybatisConfig() {
//        TKMybatisConfiguration conf = new TKMybatisConfiguration();
//        Properties properties = new Properties();
//        properties.setProperty("notEmpty", "true");
//        conf.setMapperProperties(properties);
//        return conf;
//    }

//    @Bean
//    public SqlSessionFactory zebraSqlSessionFactory(@Qualifier("merchant-master") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        // 设置查找器
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.antplatform.admin.biz.model");
//        sqlSessionFactoryBean.setConfiguration(mybatisConfig());
//        Interceptor pageInterceptor = new PageInterceptor();
//        Properties properties = new Properties();
//        properties.setProperty("dialectClass", "com.dianping.zebra.dao.dialect.MySQLDialect");
//        pageInterceptor.setProperties(properties);
//        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});
//        return sqlSessionFactoryBean.getObject();
//    }


    @Primary
    @Bean(name = "mysqlSessionFactory")
    public SqlSessionFactory mysqlSessionFactory(@Qualifier("master") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath*:mapper/*.xml"));

        //分页插件
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
//        properties.setProperty("dialectClass", "com.dianping.zebra.dao.dialect.MySQLDialect");

        properties.setProperty("dialectClass", "com.antplatform.admin.biz.infrastructure.dialect.MySQLDialect");
        Interceptor interceptor = new PageInterceptor();
        interceptor.setProperties(properties);
        sessionFactory.setPlugins(new Interceptor[] {interceptor});

        return sessionFactory.getObject();
    }

//    @Bean(name = "redisStoreClient")
//    public RedisClientBeanFactory getRedisStoreClient() {
//        RedisClientBeanFactory redisClientBeanFactory = new RedisClientBeanFactory();
//        redisClientBeanFactory.setClusterName("redis-movie");
//        redisClientBeanFactory.setReadTimeout(1000);
//        redisClientBeanFactory.setRouterType("master-slave");
//        return redisClientBeanFactory;
//    }

}
