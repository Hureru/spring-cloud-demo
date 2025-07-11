package com.hureru.order.feign.fallback;
import java.math.BigDecimal;

import com.hureru.order.feign.ProductFeignClient;
import com.hureru.product.bean.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignClientFallback implements ProductFeignClient {
    @Override
    public Product getProduct(Long productId) {
        System.out.println("兜底回调...");
        Product product = new Product();
        product.setId(productId);
        product.setProductName("未知商品");
        product.setPrice(new BigDecimal("0"));
        product.setNum(0);
        return product;
    }
}
