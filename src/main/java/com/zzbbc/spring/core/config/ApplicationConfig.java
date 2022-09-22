package com.zzbbc.spring.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource({"classpath:application_${envTarget:dev}.yml"})
public class ApplicationConfig {
    @Autowired
    Environment env;

    public String getProperty(String keyName) {
        return env.getProperty(keyName);
    }
}
