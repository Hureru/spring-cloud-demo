package com.hureru.order;

import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@SpringBootTest
public class DiscoveryClientTest {
    @Autowired
    DiscoveryClient discoveryClient;

    @Test
    public void discoveryClientTest() {
        for (String service : discoveryClient.getServices()) {
            System.out.println("service: " + service);
            for (ServiceInstance instance : discoveryClient.getInstances(service)) {
                System.out.println("ip:"+instance.getHost()+"; port:" + instance.getPort());
            }
        }
    }

    @Autowired
    NacosDiscoveryClient nacosDiscoveryCLient;

    @Test
    public void nacosDiscoveryClientTest() {
        for (String service : nacosDiscoveryCLient.getServices()) {
            System.out.println("service: " + service);
            for (ServiceInstance instance : nacosDiscoveryCLient.getInstances(service)) {
                System.out.println("ip:"+instance.getHost()+"; port:" + instance.getPort());
            }
        }
    }
}
