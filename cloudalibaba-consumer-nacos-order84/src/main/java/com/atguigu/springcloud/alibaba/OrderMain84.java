package com.atguigu.springcloud.alibaba;

import com.atguigu.springcloud.alibaba.config.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author NulLV
 * @create 2023年12月07日
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@RibbonClient(name = "cloudalibaba-provider-payment", configuration = MySelfRule.class)
public class OrderMain84 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain84.class, args);
    }
}