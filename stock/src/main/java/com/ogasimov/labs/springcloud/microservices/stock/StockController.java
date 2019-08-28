package com.ogasimov.labs.springcloud.microservices.stock;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class StockController {
    @Autowired
    private StockService stockService;

    @DeleteMapping("/stock")
    @HystrixCommand
    public void minusFromStock(@RequestBody List<Integer> menuItems) {
        stockService.minusFromStock(menuItems);

    }


}
