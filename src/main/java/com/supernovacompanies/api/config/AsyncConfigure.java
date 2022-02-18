package com.supernovacompanies.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfigure {

    @Bean
    public Executor ApiDocAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int corePoolSize = 10;
        executor.setCorePoolSize(corePoolSize);
        int maxPoolSize = 100;
        executor.setMaxPoolSize(maxPoolSize);
        int queueCapacity = 100;
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("ApiDocAsynExecutor-");
        executor.initialize();
        return executor;
    }

}
