package com.atguigu.springcloud.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author NulLV
 * @create 2023年11月29日
 */

@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule() {
        return new RandomRule(); // 随机
    }
}