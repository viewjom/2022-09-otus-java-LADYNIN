package ru.otus.crm.service;

import java.sql.Connection;

public interface Jdbc {

    byte[]  getFile(Connection connection, Long id);
    void updateDoc(Connection connection, Long id, String value);

    void updateDocHash (Connection connection, Long id, String value);
}
