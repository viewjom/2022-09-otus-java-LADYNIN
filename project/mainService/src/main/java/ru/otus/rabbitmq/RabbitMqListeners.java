//package ru.otus.rabbitmq;
/*
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.gost.UsageSample;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Slf4j
@Component
public class RabbitMqListeners {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqListeners.class);
    private final DBServiceClient dbServiceClient;

    public RabbitMqListeners(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    private String getHex(byte[] fileBytes) {
        UsageSample usageSample = new UsageSample();
        try {
            return usageSample.getHex(fileBytes);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "approval-results-queue")
    public void genHash(Long id) {


        logger.info("RabbitListener id={}", id.toString());

        var doc = dbServiceClient.loadDocument(id);
        var fileBytes = doc.getFile();

        dbServiceClient.updateDocument(id, getHex(fileBytes));

    }
}

 */