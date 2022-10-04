package com.zzbbc.spring.core;

import java.util.concurrent.Executor;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.zzbbc.spring.core.tasks.MdcTaskDecorator;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class CoreApplication extends AsyncConfigurerSupport {

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setTaskDecorator(new MdcTaskDecorator());
		executor.initialize();
		return executor;
	}

	public static void main(String[] args) {
		System.setProperty("Log4jContextSelector",
				"org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

		SpringApplication application = new SpringApplication(CoreApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}

}
