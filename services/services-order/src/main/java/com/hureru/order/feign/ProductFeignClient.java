package com.hureru.order.feign;

import com.hureru.product.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "services-product", contextId = "product-feignclient") // contextId 用于在配置OpenFeign文件时使用，默认是value值
public interface ProductFeignClient {

    @GetMapping("/product/{id}")
    Product getProduct(@PathVariable("id") Long productId);
}
