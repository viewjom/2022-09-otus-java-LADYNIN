package ru.otus.kafka;

public interface ValueSource {
    void generate();
    void accept(Long id, String value);
}