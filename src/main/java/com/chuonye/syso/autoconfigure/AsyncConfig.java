package com.chuonye.syso.autoconfigure;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 配置异步/定时任务线程池
 * 
 * @author chuonye@foxmail.com
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {
    
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(2);
        executor.setCorePoolSize(1);
        executor.setQueueCapacity(4);
        executor.setThreadNamePrefix("async-task-thread-pool-");
        executor.initialize();
        return executor;
    }
    
}
