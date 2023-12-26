package com.atguigu.springcloud.controller;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author NulLV
 * @create 2023年12月16日
 */
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("read1")
    public String getKey1() {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("OvO");
        //读之前加读锁，读锁的作用就是等待该lockkey释放写锁以后再读
        RLock rLock = readWriteLock.readLock();

        try {
            rLock.lock();
            String goods = (String) redisTemplate.opsForValue().get("goods");
            System.out.println("一号数据 = " + goods);
            return goods;
        } finally {
            rLock.unlock();
        }
    }

    @GetMapping("read2")
    public String getKey2() throws Exception {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("OvO");
        //读之前加读锁，读锁的作用就是等待该lockkey释放写锁以后再读
        RLock rLock = readWriteLock.readLock();

        try {
            rLock.lock();
            String goods = (String) redisTemplate.opsForValue().get("goods");
            System.out.println("二号数据 = " + goods);
            return goods;
        } finally {
            rLock.unlock();
        }
    }


    @GetMapping("write")
    public String writeKey() throws Exception {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("OvO");
        //写之前加写锁，写锁加锁成功，读锁只能等待
        RLock rLock = readWriteLock.writeLock();

        String goodsName = "";
        try {
            rLock.lock();
            goodsName = UUID.randomUUID().toString();
            Thread.sleep(10000);
            redisTemplate.opsForValue().set("goods", "娃哈哈" + goodsName);

            // 延迟双删
            // redisTemplate.delete("goods");
        } finally {
            rLock.unlock();
        }

        return "修改完成： " + "娃哈哈" + goodsName;
    }

}