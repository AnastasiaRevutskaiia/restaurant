package com.ogasimov.labs.springcloud.microservices.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/{tableId}")
    public Integer createOrder(@PathVariable("tableId") Integer tableId, @RequestBody List<Integer> menuItems) {
        return orderService.createOrder(tableId, menuItems);
    }
}
