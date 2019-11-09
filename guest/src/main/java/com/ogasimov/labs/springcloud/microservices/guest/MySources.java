package com.ogasimov.labs.springcloud.microservices.guest;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySources {
    @Output
    MessageChannel table();

    @Output
    MessageChannel order();

    @Output
    MessageChannel bill();

}
