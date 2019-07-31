package com.ogasimov.labs.springcloud.microservices.guest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class GuestApp {
    public static void main(String[] args) {
        SpringApplication.run(GuestApp.class, args);
    }
}
