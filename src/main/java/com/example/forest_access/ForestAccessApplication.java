package com.example.forest_access;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;


@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableMethodSecurity
public class ForestAccessApplication{

	public static void main(String[] args) {
		SpringApplication.run(ForestAccessApplication.class, args);
	}

	@Bean("pool-01")
	public TaskExecutor getTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("pool-01");
		executor.setAllowCoreThreadTimeOut(true);
		executor.setKeepAliveSeconds(60);
		executor.initialize();
		return executor;
	}
}
