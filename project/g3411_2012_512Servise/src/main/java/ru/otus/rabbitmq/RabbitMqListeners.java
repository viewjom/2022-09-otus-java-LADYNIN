package ru.otus.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.otus.service.ServiceGost3411;
import ru.otus.service.DBServiceClient;

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

        ServiceGost3411 usageSample = new ServiceGost3411();
        try {
            return usageSample.getHex(fileBytes);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }

    }

    @RabbitListener(queues = "approval-results-queue")
    public void genHash(String message) {
        try {
            logger.info("RabbitListener document_id={}", message);

            var file = dbServiceClient.loadFile(Long.valueOf(message));

            dbServiceClient.updateDocument(Long.valueOf(message), getHex(file));
        } catch (NullPointerException e) {

        }

    }
}