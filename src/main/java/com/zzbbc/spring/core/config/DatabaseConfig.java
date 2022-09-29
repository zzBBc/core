package com.zzbbc.spring.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

@Configuration
public class DatabaseConfig extends AbstractR2dbcConfiguration {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(H2ConnectionConfiguration.builder().inMemory("cb-rate-db")
                .username(username).password(password).build());

        // return ConnectionFactories.get(
        // ConnectionFactoryOptions.builder().option(ConnectionFactoryOptions.DATABASE, url)
        // .option(ConnectionFactoryOptions.USER, username)
        // .option(ConnectionFactoryOptions.PASSWORD, password)
        // .option(ConnectionFactoryOptions.DRIVER, driverClassName).build());
    }

}
