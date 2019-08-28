package com.ogasimov.labs.springcloud.microservices.order;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/{tableId}")
    @HystrixCommand
    public Integer createOrder(@PathVariable("tableId") Integer tableId, @RequestBody List<Integer> menuItems) {
        return orderService.createOrder(tableId, menuItems);
    }

}
