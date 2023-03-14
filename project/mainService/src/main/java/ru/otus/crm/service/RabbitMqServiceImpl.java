package ru.otus.crm.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqServiceImpl implements RabbitMqService {

    public static final String MAIN_EXCHANGE = "main-exchange";

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Override
    public void sendDocumentHashEvent(Long id) {

        rabbitTemplate.convertAndSend("document.hash512", id);
    }

}