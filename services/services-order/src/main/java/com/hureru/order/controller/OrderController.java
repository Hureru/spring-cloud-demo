package com.hureru.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hureru.order.bean.Order;
import com.hureru.order.properties.OrderProperties;
import com.hureru.order.service.Impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

//@RefreshScope // 配置自动刷新功能
@RestController
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;
//    @Value("${order.timeout}")
//    String orderTimeout;
//    @Value("${order.auto-confirm}")
//    String orderAutoConfirm;
    @Autowired
    OrderProperties orderProperties;

    public OrderController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @GetMapping("config")
    public String config() {
        return "orderTimeout = " + orderProperties.getTimeout() + ", orderAutoConfirm = " + orderProperties.getAutoConfirm();
    }

    @GetMapping("/order")
    public Order getOrder(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        return orderServiceImpl.getOrderByUserIdAndProductId(userId , productId);
    }

    @GetMapping("/seckill")
    @SentinelResource(value = "seckill-order", fallback = "seckillFallback")
    public Order seckill(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        return orderServiceImpl.getOrderByUserIdAndProductId(userId , Long.MAX_VALUE);
    }

    public Order seckillFallback(Long userId, Long productId, BlockException e) {
        Order order = new Order();
        order.setId(0);
        order.setTotalAmount(new BigDecimal("0"));
        order.setUserId(userId);
        order.setNickName("未知用户");
        order.setAddress("异常原因："+e.getClass());
        return order;
    }
}
