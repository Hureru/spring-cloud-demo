package com.hureru.order.controller;

import com.hureru.order.bean.Order;
import com.hureru.order.properties.OrderProperties;
import com.hureru.order.service.Impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
