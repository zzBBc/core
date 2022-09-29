package com.zzbbc.spring.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

@Configuration
@EnableR2dbcRepositories(basePackages = {"com.zzbbc.spring.core.repositories.impl"})
public class DatabaseConfig extends AbstractR2dbcConfiguration {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    @Bean
    public ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(H2ConnectionConfiguration.builder().url(url)
                .username(username).password(password).build());

        // return ConnectionFactories.get(
        // ConnectionFactoryOptions.builder().option(ConnectionFactoryOptions.DATABASE, url)
        // .option(ConnectionFactoryOptions.USER, username)
        // .option(ConnectionFactoryOptions.PASSWORD, password).build());
    }

}
