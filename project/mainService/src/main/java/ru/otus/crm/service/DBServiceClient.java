package ru.otus.crm.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Document;
import ru.otus.json.model.Result;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    RedirectView saveClient( List<Result> result, String name, String number, String guid, MultipartFile[] fils);

    Optional<Client> getClient(long id);

    List<Client> findAll();

    Document loadDocument(Long documentId);
    Long getLastId();
    void updateDocument(Long id, String hash);
}