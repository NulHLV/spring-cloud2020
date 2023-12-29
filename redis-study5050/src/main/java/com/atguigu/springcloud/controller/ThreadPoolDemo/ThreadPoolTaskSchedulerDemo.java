package com.atguigu.springcloud.controller.ThreadPoolDemo;

import cn.hutool.core.date.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author NulLV
 * @create 2023年12月28日
 */

@Service
public class ThreadPoolTaskSchedulerDemo {

    @Scheduled(initialDelay = 5000, fixedDelay = 5000) // 示例：每5秒执行一次
    public void test1() throws InterruptedException {
        System.out.println("【" + DateUtil.now() + "】 " + Thread.currentThread().getName() + " test1执行开始...");
        Thread.sleep(10000);
        System.out.println("【" + DateUtil.now() + "】 " + Thread.currentThread().getName() + " test1执行完毕...\n");
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 5000) // 示例：每5秒执行一次
    public void test2() throws InterruptedException {
        System.out.println("【" + DateUtil.now() + "】 " + Thread.currentThread().getName() + " test2执行开始...");
        Thread.sleep(15000);
        System.out.println("【" + DateUtil.now() + "】 " + Thread.currentThread().getName() + " test2执行完毕...\n");
    }
}