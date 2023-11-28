package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("comsumer")
public class OrderController {

    public static final String PAYENT_URL = "http://localhost:8001";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYENT_URL + "/payment/get/"+id, CommonResult.class);
    }

    @GetMapping("payment/create")
    public CommonResult<Payment> create(Payment payment) {
        return restTemplate.postForObject(PAYENT_URL + "/payment/create", payment, CommonResult.class);  //写操作
    }
}
