package com.atguigu.springcloud.controller.ThreadPoolDemo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;

import java.util.concurrent.*;

/**
 * @author NulLV
 * @create 2023年12月26日
 */

public class ScheduleThreadPoolDemo {
    public static void main(String[] args) {
        // 创建ScheduledThreadPoolExecutor
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(
                3,
                new MyCustomThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        Runnable runnable1 = () -> {
            System.out.println(DateUtil.now() + "\t" + Thread.currentThread().getName() + "\t开始执行");
        };

        Runnable runnable2 = () -> {
            System.out.println(DateUtil.now() + "\t" + Thread.currentThread().getName() + "\t开始执行");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(DateUtil.now() + "\t" + Thread.currentThread().getName() + "\t执行完毕\n");
        };

        Runnable runnable3 = () -> {
            System.out.println(DateUtil.now() + "\t" + Thread.currentThread().getName() + "\t开始执行");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(DateUtil.now() + "\t" + Thread.currentThread().getName() + "\t执行完毕\n");
        };

        // 每隔5s开始执行。需手动指定时间
        // scheduledExecutorService.schedule(runnable1, 5, TimeUnit.SECONDS);
        // scheduledExecutorService.schedule(runnable2, 10, TimeUnit.SECONDS);
        // scheduledExecutorService.schedule(runnable3, 15, TimeUnit.SECONDS);

        // 如果任务的执行时间超过了固定的执行周期，那么下一个任务会立即开始执行，不管前一个任务是否已经完成。
        // scheduledExecutorService.scheduleAtFixedRate(runnable2, 0, 5, TimeUnit.SECONDS);

        // 如果任务的执行时间超过了固定的延迟时间，后续任务会在上一个任务完成后等待整个固定的延迟周期后开始执行。
        scheduledExecutorService.scheduleWithFixedDelay(runnable3, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * 自定义线程工厂
     *
     * @author NulLV
     * @creat: 2023-12-26
     */
    static class MyCustomThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("【NulLV-Pool-" + IdUtil.simpleUUID() + "】");
            return thread;
        }
    }
}