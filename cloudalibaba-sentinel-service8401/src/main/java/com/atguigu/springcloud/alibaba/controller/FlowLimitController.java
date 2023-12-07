package com.atguigu.springcloud.alibaba.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.alibaba.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class FlowLimitController {
    @Autowired
    TestService testService;

    @GetMapping("/testA")
    public String testA() {
        return "------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "------testB";
    }

    @SentinelResource(value = "testD", fallback = "fallbackForTestD")
    @GetMapping("/testD")
    public String testD() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("testD 测试RT " + IdUtil.simpleUUID());
        return "------testD";
    }

    @GetMapping("/testE")
    public String testE() {
        int a = 10 / 0;
        System.out.println("testE 测试RT " + IdUtil.simpleUUID());
        return "------testE";
    }

    @GetMapping("/testF")
    @SentinelResource(value = "testF", defaultFallback = "defaultFallback")
    public String testF(@RequestParam(value = "p1", required = false) String p1,
                        @RequestParam(value = "p2", required = false) String p2) {
        System.out.println("testE 测试RT " + IdUtil.simpleUUID());
        return "------testF\t" + p1 + "\t" + p2;
    }

    // fallback和blockHandler都指定，只后者生效
    public String fallbackForTestD() {
        return "fallbackForTestD 我给您兜底了";
    }

    public static String blockHandlerForTestD(BlockException ex) {
        return "我给您兜底了";
    }

    public String defaultFallback() {
        return "默认兜底限流";
    }
}