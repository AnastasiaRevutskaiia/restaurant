package com.ogasimov.labs.springcloud.microservices.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableBinding(MyChannels.class)
public class OrderApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
    }
}
