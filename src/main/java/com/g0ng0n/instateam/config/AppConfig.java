package com.g0ng0n.instateam.config;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by g0ng0n.
 */
@Configuration
@PropertySource("app.properties")
public class AppConfig {

    @Autowired
    private Environment enviroment;

    @Bean
    public Hashids hashids(){
        return new Hashids(enviroment.getProperty("instateam.hash.salt"),8);
    }
}
