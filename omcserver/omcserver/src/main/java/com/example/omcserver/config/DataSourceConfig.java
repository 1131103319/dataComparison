package com.example.omcserver.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceConfig {
    /**
     * 创建 springboot 的数据库的数据源 DataSource
     * @return
     */
    @Bean("dataSourceOne")
    @ConfigurationProperties("spring.datasource.one")
    public DataSource dataSourceOne(){

        return DruidDataSourceBuilder.create().build();
    }
    @Bean("jdbcTemplateOne")
    JdbcTemplate jdbcTemplateOne(@Qualifier("dataSourceOne") DataSource dataSourceOne){
        return new JdbcTemplate(dataSourceOne);
    }

    /**
     * 创建 springboot2 的数据库的数据源 DataSource
     * @return
     */
    @Bean("dataSourceTwo")
    @ConfigurationProperties("spring.datasource.two")
    public DataSource dataSourceTwo(){

        return DruidDataSourceBuilder.create().build();
    }
    @Bean("jdbcTemplateTwo")
    JdbcTemplate jdbcTemplateTwo(@Qualifier("dataSourceTwo") DataSource dataSourceTwo){
        return new JdbcTemplate(dataSourceTwo);
    }
}
