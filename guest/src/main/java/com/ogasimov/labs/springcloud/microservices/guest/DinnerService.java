package com.ogasimov.labs.springcloud.microservices.guest;

import com.ogasimov.labs.springcloud.microservices.common.CreateOrderCommand;
import com.ogasimov.labs.springcloud.microservices.common.FreeTableCommand;
import com.ogasimov.labs.springcloud.microservices.common.OccupyTableCommand;
import com.ogasimov.labs.springcloud.microservices.common.PayBillCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
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

    @Autowired
    private MySources sources;

    public Integer startDinner(List<Integer> menuItems) {
        //check free tables
        List<Integer> freeTables = tableClient.getFreeTables();
        if (freeTables.size() == 0) {
            throw new RuntimeException("No free tables available.");
        }

        //occupy a table
        final Integer tableId = freeTables.get(0);
        sources.table().send(MessageBuilder.withPayload(
                new OccupyTableCommand(tableId)).build()
        );
        //tableClient.occupyTable(tableId);


        //create the order
        sources.order().send(MessageBuilder.withPayload(
                new CreateOrderCommand(tableId, menuItems)).build()
        );
        //orderClient.createOrder(tableId, menuItems);

        return tableId;
    }

    public void finishDinner(Integer tableId) {
        //pay bill
        sources.bill().send(MessageBuilder.withPayload(
                new PayBillCommand(tableId)).build()
        );
        //billClient.payBills(tableId);


        //free table
        sources.table().send(MessageBuilder.withPayload(
                new FreeTableCommand(tableId)).build()
        );
        //tableClient.freeTable(tableId);
    }
}
