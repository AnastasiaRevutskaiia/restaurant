package com.ogasimov.labs.springcloud.microservices.table;

import com.ogasimov.labs.springcloud.microservices.common.AbstractTableCommand;
import com.ogasimov.labs.springcloud.microservices.common.FreeTableCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TableService {
    @Autowired
    private TableRepository tableRepository;

    @StreamListener(Sink.INPUT)
    private void streamListener(AbstractTableCommand tableCommand) {
        boolean isFree = (tableCommand instanceof FreeTableCommand);
        updateTable(tableCommand.getTableId(), isFree);
    }

    public List<Integer> getTableIds() {
        return tableRepository.findAll()
                .stream()
                .map(Table::getId)
                .collect(Collectors.toList());
    }

    public List<Integer> getFreeTableIds() {
        return tableRepository.findAllByFree(Boolean.TRUE)
                .stream()
                .map(Table::getId)
                .collect(Collectors.toList());
    }

    public void updateTable(Integer id, boolean isFree) {
        Table table = tableRepository.findOne(id);
        if (table == null) {
            throw  new EntityNotFoundException("Table not found");
        }
        table.setFree(isFree);
        tableRepository.save(table);
    }

}
