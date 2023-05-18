package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.model.Document;

import java.util.List;

public interface ClientRepository extends CrudRepository<Document, Long> {

    List<Document> findAll();
}
