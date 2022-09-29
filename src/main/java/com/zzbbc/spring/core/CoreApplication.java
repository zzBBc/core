package com.zzbbc.spring.core;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = {"com.zzbbc.spring.core.repositories.impl"})
public class CoreApplication {
	public static void main(String[] args) {
		System.setProperty("Log4jContextSelector",
				"org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

		SpringApplication application = new SpringApplication(CoreApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}

}
