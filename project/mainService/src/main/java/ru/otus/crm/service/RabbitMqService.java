package ru.otus.crm.service;
public interface RabbitMqService {
    void sendDocumentHashEvent(Long id);
}
