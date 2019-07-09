package com.example.chat.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

/**
 * Created by rui on 09/07/2019
 */

@Configuration
@PropertySource("app.properties")
public class DatabaseConfig {
    @Autowired
    private Environment environment;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        Resource resource = new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setConfigLocation(resource);
        sessionFactory.setPackagesToScan(environment.getProperty("chat.entity.package"));
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("chat.db.Driver"));
        dataSource.setUrl(environment.getProperty("chat.db.url"));
        dataSource.setUsername(environment.getProperty("chat.db.username"));
        dataSource.setPassword(environment.getProperty("chat.db.password"));
        return dataSource;
    }
}
