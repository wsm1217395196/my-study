package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    /**
     * spring的Task线程池
     *
     * @return
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);//配置核心线程数
        executor.setMaxPoolSize(20);//配置最大线程数
        executor.setQueueCapacity(100);//配置队列大小
        executor.setThreadNamePrefix("threadPool-");//线程的名称前缀
        //Caller_runs:不在新线程中执行任务，而是在调用者所在线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * java 线程池
     *
     * @return
     */
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
                20, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPoolExecutor;
    }
}
