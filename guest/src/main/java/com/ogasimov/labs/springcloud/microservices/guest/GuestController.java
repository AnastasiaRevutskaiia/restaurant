package com.ogasimov.labs.springcloud.microservices.guest;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GuestController {
    @Autowired
    private DinnerService dinnerService;

    @PostMapping("/dinner")
    public Integer startDinner(@RequestBody List<Integer> menuItems) {
        return dinnerService.startDinner(menuItems);
    }

    @DeleteMapping("/dinner/{tableId}")
    @HystrixCommand
    public String finishDinner(@PathVariable Integer tableId) {
        dinnerService.finishDinner(tableId);
        return "Dinner on table " + tableId + " is finished";
    }

}
