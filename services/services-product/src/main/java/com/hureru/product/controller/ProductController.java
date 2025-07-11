package com.hureru.product.controller;

import com.hureru.product.bean.Product;
import com.hureru.product.service.Impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long productId, HttpServletRequest request) {
        String header = request.getHeader("X-Token");
        System.out.println("hello ..... token: ["+ header +"]");
        return productServiceImpl.getProductById(productId);
    }
}
