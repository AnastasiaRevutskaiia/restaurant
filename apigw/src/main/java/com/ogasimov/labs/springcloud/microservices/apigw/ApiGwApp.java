package com.ogasimov.labs.springcloud.microservices.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableHystrix
@EnableDiscoveryClient
@EnableZuulProxy
public class ApiGwApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiGwApp.class, args);
    }
}
