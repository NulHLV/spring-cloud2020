package com.atguigu.springcloud.controller.ThreadPoolDemo.config;

import cn.hutool.core.util.IdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * @author NulLV
 * @create 2023年12月28日
 */


@Configuration
@EnableScheduling
public class TaskSchedulerConfig {
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.initialize();
        scheduler.setThreadFactory(new MyFactory());

        // 获取 ThreadPoolExecutor
        ScheduledThreadPoolExecutor executor = scheduler.getScheduledThreadPoolExecutor();
        executor.setMaximumPoolSize(1);

        // 打印线程池信息
        System.out.println("核心线程数：" + executor.getCorePoolSize());
        System.out.println("最大线程数：" + executor.getMaximumPoolSize());
        System.out.println("活动线程数：" + executor.getActiveCount());
        System.out.println("队列大小：" + executor.getQueue().size());
        System.out.println("已完成的任务数：" + executor.getCompletedTaskCount());
        System.out.println("总任务数：" + executor.getTaskCount());

        return scheduler;
    }

    static class MyFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("【NulLV-Pool-" + IdUtil.simpleUUID() + "】");
            return thread;
        }
    }
}