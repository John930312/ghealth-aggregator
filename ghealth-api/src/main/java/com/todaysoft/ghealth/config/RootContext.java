package com.todaysoft.ghealth.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ComponentScan("com.todaysoft.ghealth")
@EnableAspectJAutoProxy
@EnableTransactionManagement
@MapperScan("com.todaysoft.ghealth.mybatis.mapper")
@PropertySource({"classpath:application-context.properties"})
public class RootContext implements ApplicationContextAware
{
    private static ApplicationContext context;
    
    @Autowired
    private Environment environment;
    
    @Bean(name = "characterEncodingFilter")
    public CharacterEncodingFilter getCharacterEncodingFilter()
    {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("utf-8");
        return filter;
    }
    
    @Bean(name = "dataSource")
    public DataSource getDataSource() throws SQLException
    {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("datasource.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("datasource.url"));
        dataSource.setUsername(environment.getRequiredProperty("datasource.username"));
        dataSource.setPassword(environment.getRequiredProperty("datasource.password"));
        dataSource.setFilters("stat,config");
        
        dataSource.setInitialSize(environment.getRequiredProperty("datasource.initial", int.class));
        dataSource.setMaxActive(environment.getRequiredProperty("datasource.max.active", int.class));
        dataSource.setMinIdle(environment.getRequiredProperty("datasource.min.idle", int.class));
        dataSource.setMaxWait(environment.getRequiredProperty("datasource.max.wait", long.class));
        
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(30000);
        
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeoutMillis(1800000);
        
        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setTestOnReturn(false);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestWhileIdle(true);
        
        Properties properties = new Properties();
        properties.setProperty("config.decrypt", "true");
        properties.setProperty("config.decrypt.key", environment.getRequiredProperty("datasource.public.key"));
        dataSource.setConnectProperties(properties);
        return dataSource;
    }
    
    @Bean(name = "aliyunOSSConfig")
    public AliyunOSSConfig getAliyunOSSConfig()
    {
        AliyunOSSConfig config = new AliyunOSSConfig();
        config.setAccessKeyId(environment.getProperty("aliyun.oss.access.key", ""));
        config.setAccessKeySecret(environment.getProperty("aliyun.oss.access.secret", ""));
        config.setEndpoint(environment.getProperty("aliyun.oss.endpoint", ""));
        config.setBucketName(environment.getProperty("aliyun.oss.bucket.name", ""));
        config.setDirectoryName(environment.getProperty("aliyun.oss.directory.name", ""));
        return config;
    }
    
    @Bean(name = "objectStorageConfig")
    public ObjectStorageConfig getObjectStorageConfig()
    {
        ObjectStorageConfig config = new ObjectStorageConfig();
        config.setStorageType(environment.getRequiredProperty("object.storage.type"));
        return config;
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager() throws SQLException
    {
        return new DataSourceTransactionManager(getDataSource());
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception
    {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        return sessionFactory.getObject();
    }
    
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException
    {
        RootContext.context = context;
    }
    
    public static <T> T getBean(Class<T> requiredType)
    {
        return context.getBean(requiredType);
    }
}
