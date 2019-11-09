package com.ogasimov.labs.springcloud.microservices.order;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MyChannels {
    String ORDER = "order";

    @Input(ORDER)
    SubscribableChannel order();

    @Output
    MessageChannel stock();

    @Output
    MessageChannel bill();
}

