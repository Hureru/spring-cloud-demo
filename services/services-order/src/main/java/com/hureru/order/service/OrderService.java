package com.hureru.order.service;


import com.hureru.order.bean.Order;

public interface OrderService {
    Order getOrderByUserIdAndProductId(Long userId, Long ProductId);
}
