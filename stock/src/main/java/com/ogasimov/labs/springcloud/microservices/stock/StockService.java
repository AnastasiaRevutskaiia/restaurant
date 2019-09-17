package com.ogasimov.labs.springcloud.microservices.stock;

import com.ogasimov.labs.springcloud.microservices.common.AbstractStockCommand;
import com.ogasimov.labs.springcloud.microservices.common.MinusStockCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @StreamListener(Sink.INPUT)
    private void streamListener(AbstractStockCommand stockCommand) {
        if (stockCommand instanceof MinusStockCommand) {
            minusFromStock(stockCommand.getMenuItems());
        }
    }

    public void minusFromStock(List<Integer> menuItems) throws EntityNotFoundException {
        menuItems.forEach(menuItemId -> {
            Stock stock = stockRepository.findOneByMenuItemId(menuItemId);
            if (stock == null) {
                throw  new EntityNotFoundException("Stock not found: " + menuItemId);
            }
            if (stock.getCount() == 0) {
                throw  new EntityNotFoundException("Stock empty: " + menuItemId);
            }
            stock.setCount(stock.getCount() - 1);
            stockRepository.save(stock);
        });

    }
}
