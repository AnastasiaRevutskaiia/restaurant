package com.ogasimov.labs.springcloud.microservices.bill;

import com.ogasimov.labs.springcloud.microservices.common.AbstractBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.CreateBillCommand;
import com.ogasimov.labs.springcloud.microservices.common.PayBillCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BillService {
    @Autowired
    private BillRepository billRepository;

    @StreamListener(Sink.INPUT)
    private void billListener(AbstractBillCommand billCommand) {
        if (billCommand instanceof CreateBillCommand) {
            createBill(
                    billCommand.getTableId(),
                    ((CreateBillCommand) billCommand).getOrderId()
            );
        } else if (billCommand instanceof PayBillCommand) {
            payBills(billCommand.getTableId());
        }
    }

    public void createBill(Integer tableId, Integer orderId) {
        Bill bill = new Bill();
        bill.setTableId(tableId);
        bill.setOrderId(orderId);
        billRepository.save(bill);
    }

    public void payBills(Integer tableId) {
        List<Bill> bills = billRepository.findAllByTableId(tableId);
        if (bills.isEmpty()) {
            throw  new EntityNotFoundException("Bills not found");
        }
        billRepository.delete(bills);
    }
}
