package com.ogasimov.labs.springcloud.microservices.order;

import com.ogasimov.labs.springcloud.microservices.common.AbstractOrderCommand;
import com.ogasimov.labs.springcloud.microservices.common.CreateBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.CreateOrderCommand;
import com.ogasimov.labs.springcloud.microservices.common.MinusStockCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StockClient stockClient;

    @Autowired
    private BillClient billClient;

    @Autowired
    private MyChannels channels;

    @StreamListener(MyChannels.ORDER)
    private void streamListener(AbstractOrderCommand orderCommand) {
        if (orderCommand instanceof CreateOrderCommand) {
            createOrder(
                    orderCommand.getTableId(),
                    ((CreateOrderCommand) orderCommand).getMenuItems()
            );
        }
    }

    public Integer createOrder(Integer tableId, List<Integer> menuItems) {

        Order order = new Order();
        order.setTableId(tableId);
        orderRepository.save(order);

        final Integer orderId = order.getId();

        // minus menuItems from stock
        channels.stock().send(
                MessageBuilder.withPayload(new MinusStockCommand(menuItems)).build()
        );
        // stockClient.minusFromStock(menuItems);


        //create bill
        channels.bill().send(
                MessageBuilder.withPayload(new CreateBillCommand(tableId, orderId)).build()
        );
        //billClient.createBill(tableId, orderId);

        return orderId;
    }
}
