package ru.otus.service;


public interface DBServiceClient {

    byte[] loadFile(Long documentId);
    void updateDocument(Long id, String hash);
}