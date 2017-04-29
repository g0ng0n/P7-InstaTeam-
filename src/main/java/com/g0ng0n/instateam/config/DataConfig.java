package com.g0ng0n.instateam.config;

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
 * Created by g0ng0n.
 */
@Configuration
@PropertySource("app.properties")
public class DataConfig {

    @Autowired
    private Environment environment;

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        Resource config = new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setConfigLocation(config);
        sessionFactoryBean.setPackagesToScan(environment.getProperty("instateam.entity.package"));
        sessionFactoryBean.setDataSource(dataSource());

        return sessionFactoryBean;
    }

    @Bean
    public DataSource dataSource() {

        BasicDataSource dataSource = new BasicDataSource();

        // Setting The Driver Class Name from the apps properties file
        dataSource.setDriverClassName(environment.getProperty("instateam.db.driver"));

        // Setting The URL from the apps properties file
        dataSource.setUrl(environment.getProperty("instateam.db.url"));

        // Setting The Username from the apps properties file
        dataSource.setUsername(environment.getProperty("instateam.db.username"));

        // Setting The Password from the apps properties file, this should be hashed
        dataSource.setPassword(environment.getProperty("instateam.db.password"));

        return dataSource;
    }
}
