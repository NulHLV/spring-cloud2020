package com.atguigu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.service.PaymentOpenFeignService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("consumer")
public class OrderFeignController {

    @Resource
    private PaymentOpenFeignService paymentOpenFeignService;

    @GetMapping(value = "payment/get/{id}")
    // @SentinelResource(value = "fallback", fallback = "paymentSQL_fallback")
    // @SentinelResource(value = "fallback", blockHandler = "paymentSQL_blockHandler")
    @SentinelResource(value = "fallback", fallback = "paymentSQL_fallback", blockHandler = "paymentSQL_blockHandler")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        if (id == 4) {
            throw new RuntimeException("非法参数异常");
        } else if (id == 5) {
            throw new NullPointerException("该ID没有对应数据,空指针异常");
        }
        return paymentOpenFeignService.paymentSQL(id);
    }

    @GetMapping(value = "payment/getConfigInfo")
    public String paymentSQL() {
        return paymentOpenFeignService.getConfigInfo();
    }

    // fallback可以管运行异常
    public CommonResult<Payment> paymentSQL_fallback(@PathVariable("id") Long id, Throwable e) {
        Payment payment = new Payment(id, null);
        return new CommonResult<>(444, "兜底异常  " + e.getMessage(), payment);
    }

    // blockHandler管配置异常
    public CommonResult<Payment> paymentSQL_blockHandler(@PathVariable("id") Long id, BlockException e) {
        Payment payment = new Payment(id, null);
        return new CommonResult<>(444, "sentinel限流  " + e.getMessage(), payment);
    }
}