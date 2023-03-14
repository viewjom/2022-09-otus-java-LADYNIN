package ru.otus.kafka.consumer;

public class ConsumerException extends RuntimeException {
    public ConsumerException(String message, Throwable cause) {
        super(message, cause);
    }
}

