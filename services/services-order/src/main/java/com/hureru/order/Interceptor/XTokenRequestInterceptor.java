package com.hureru.order.Interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

// feign 自动搜索 Bean 注入
@Component
public class XTokenRequestInterceptor implements RequestInterceptor {
    /**
    *  远程调用请求拦截器
    * @param requestTemplate 请求模板
     * */

    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("XTokenRequestInterceptor ........");
        requestTemplate.header("X-Token", UUID.randomUUID().toString());
    }
}
