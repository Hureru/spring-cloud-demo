package com.hureru.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

@SpringBootTest
public class DiscoveryClientTest {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Test
    public void testDiscoveryClient() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            System.out.println(service);
            for (ServiceInstance instance : discoveryClient.getInstances(service)) {
                System.out.println("ip:"+instance.getHost()+";port:"+instance.getPort());
            }
        }
    }
}
