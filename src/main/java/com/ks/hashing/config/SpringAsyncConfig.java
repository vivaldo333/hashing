package com.ks.hashing.config;

import com.ks.hashing.api.handler.CustomAsyncExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

@Configuration
@EnableAsync
@Slf4j
public class SpringAsyncConfig implements AsyncConfigurer {
    public static final String TASK_EXECUTOR = "taskExecutor";

    @Bean(name = TASK_EXECUTOR)
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        int countOfThreads = getCoreCounts();
        threadPoolTaskExecutor.setBeanName(TASK_EXECUTOR);
        threadPoolTaskExecutor.setCorePoolSize(countOfThreads);
        threadPoolTaskExecutor.setMaxPoolSize(countOfThreads);
        //threadPoolTaskExecutor.setQueueCapacity(queue);
        threadPoolTaskExecutor.setThreadNamePrefix("async-task-thread-pool");
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setAwaitTerminationSeconds(60);
        threadPoolTaskExecutor.setRejectedExecutionHandler(
                (r, executor) -> log.warn("current thread pool is full, reject to invoke."));
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    private int getCoreCounts() {
        return Runtime.getRuntime().availableProcessors() + INTEGER_ONE;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}
