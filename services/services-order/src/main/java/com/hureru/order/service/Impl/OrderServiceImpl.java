package com.hureru.order.service.Impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hureru.order.bean.Order;
import com.hureru.order.feign.ProductFeignClient;
import com.hureru.order.service.OrderService;
import com.hureru.product.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private ProductFeignClient productFeignClient;

    @SentinelResource(value = "createOrder", blockHandler = "createOrderFallback")
    @Override
    public Order getOrderByUserIdAndProductId(Long userId, Long productId) {
//        Product product = getProductFromRemoteWithLoadBalancerAnnotation(productId);
        Product product = productFeignClient.getProduct(productId);
        Order order = new Order();
        order.setId(1);
        // 计算总金额
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("zhangsan");
        order.setAddress("五指山");
        // 商品列表
        order.setProductList(List.of(product));
        return order;
    }

    // 兜底回调
    public Order createOrderFallback(Long userId, Long productId, BlockException e){
        Order order = new Order();
        order.setId(0);
        order.setTotalAmount(new BigDecimal("0"));
        order.setUserId(userId);
        order.setNickName("未知用户");
        order.setAddress("错误信息：" + e.getClass());
        return order;
    }

    private Product getProductFromRemote(Long productId){
        //1. 获取所有可用服务器列表
        List<ServiceInstance> instances = discoveryClient.getInstances("services-product");
        ServiceInstance serviceInstance = instances.get(0);
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/product/" + productId;
        log.info("远程请求：{}", url);
        // 2. 发送请求
        Product produce = restTemplate.getForObject(url, Product.class);

        return produce;
    }

//    进阶2：完成负载均衡发送请求
    private Product getProductFromRemoteWithLoadBalancer(Long productId){
        //1. 获取所有可用服务器列表
        ServiceInstance choose = loadBalancerClient.choose("services-product");
        String url = "http://" + choose.getHost() + ":" + choose.getPort() + "/product/" + productId;
        log.info("远程请求：{}", url);
        // 2. 发送请求
        Product produce = restTemplate.getForObject(url, Product.class);

        return produce;
    }

    //    进阶3：基于注解 完成负载均衡发送请求
    private Product getProductFromRemoteWithLoadBalancerAnnotation(Long productId){
        String url = "http://services-product/product/"+productId;
        // 2. 发送请求
        Product produce = restTemplate.getForObject(url, Product.class);

        return produce;
    }
}
