package com.ogasimov.labs.springcloud.microservices.order;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockClient stockClient;

    @Autowired
    private BillClient billClient;

    public Integer createOrder(Integer tableId, List<Integer> menuItems) {

        // catch an exception
        // when in the order count of menuItems is bigger then on the stock
        // new Order with this way doesn't create, but table stays still occupy,
        // and when you try to free table, you received an Exception that "bill can't be find"
        // and like a result you can't free table using @DeleteMapping("/dinner/{tableId}"),
        // you have to do this manually in DB
        try {
            stockClient.minusFromStock(menuItems);
        } catch (EntityNotFoundException ex) {
            ex.getMessage();
        }

        Order order = new Order();
        order.setTableId(tableId);
        orderRepository.save(order);

        final Integer orderId = order.getId();
        billClient.createBill(tableId, orderId);

        return orderId;
    }
}
