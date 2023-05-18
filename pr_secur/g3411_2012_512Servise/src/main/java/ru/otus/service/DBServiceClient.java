package ru.otus.service;


public interface DBServiceClient {

    byte[] loadFile(Long documentId);
    byte[] loadFile2(Long documentId);
    void updateDocument(Long id, String hash);
    void updateDocument2(Long id, String hash);
}