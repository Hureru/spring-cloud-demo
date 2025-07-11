package com.hureru.order.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class OrderServiceConfig {

    @Bean // 超时控制 重试机制
    Retryer retryer(){
        return new Retryer.Default();
    }

//    @Bean  // 可以通过配置spring.cloud.openfeign.client.config取代
//    Logger.Level feignLoggerLevel() {
//        return Logger.Level.FULL;
//    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
