package com.hureru.order.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 配置实体类
@Component
@ConfigurationProperties(prefix = "order")
@Data
public class OrderProperties {
    String timeout;
    String autoConfirm;
}
