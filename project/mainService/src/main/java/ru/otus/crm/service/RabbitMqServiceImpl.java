package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
@Service
public class RabbitMqServiceImpl implements RabbitMqService {
    private static final Logger log = LoggerFactory.getLogger(RabbitMqServiceImpl.class);
    public static final String MAIN_EXCHANGE = "main-exchange";

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Override
    public void sendDocumentHashEvent(Long id) {
        try {
            rabbitTemplate.convertAndSend("document.hash512", id);
        } catch (AmqpConnectException e) {
            log.info("Error RabbitMq: AmqpConnectException");
        }
    }

}