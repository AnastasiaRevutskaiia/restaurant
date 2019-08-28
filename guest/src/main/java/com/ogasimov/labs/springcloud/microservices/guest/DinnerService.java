package com.ogasimov.labs.springcloud.microservices.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class DinnerService {
    @Autowired
    private TableClient tableClient;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private BillClient billClient;

    public Integer startDinner(List<Integer> menuItems) {
        //check free tables
        List<Integer> freeTables = tableClient.getFreeTables();
        if (freeTables.size() == 0) {
            throw new RuntimeException("No free tables available.");
        }

        //occupy a table
        final Integer tableId = freeTables.get(0);
        tableClient.occupyTable(tableId);

        //create the order
        orderClient.createOrder(tableId, menuItems);

        return tableId;
    }

    public void finishDinner(Integer tableId) {
        billClient.payBills(tableId);
        tableClient.freeTable(tableId);
    }
}
