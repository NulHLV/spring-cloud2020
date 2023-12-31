package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author NulLV
 * @create 2023年12月01日
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "PaymentFallbackService fall back paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "PaymentFallbackService fall back paymentInfo_TimeOut";
    }
}