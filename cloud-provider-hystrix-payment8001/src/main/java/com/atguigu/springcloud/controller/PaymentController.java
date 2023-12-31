package com.atguigu.springcloud.controller;

import cn.hutool.core.lang.UUID;
import com.atguigu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id) {
        System.out.println("paymentInfo_OK请求: " + UUID.randomUUID());
        String result = paymentService.paymentInfo_OK(id);
        return result;
    }

    @GetMapping("/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        System.out.println("paymentInfo_TimeOut请求: " + UUID.randomUUID());
        String result = paymentService.paymentInfo_TimeOut(id);
        return result;
    }

    //服务熔断
    @GetMapping("/hystrix/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        System.out.println("paymentCircuitBreaker请求: " + UUID.randomUUID());
        String result = paymentService.paymentCircuitBreaker(id);
        return result;
    }


}