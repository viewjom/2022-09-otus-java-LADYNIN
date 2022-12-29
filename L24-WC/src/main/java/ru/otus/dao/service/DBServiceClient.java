package ru.otus.dao.service;

import ru.otus.model.Client;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();

    Map<String, Object> htmlAll();
}