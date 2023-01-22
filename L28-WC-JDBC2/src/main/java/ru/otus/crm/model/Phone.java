package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table("phones")
public class Phone {
    @Id
    private final Long id;
    private final String number;

    private final Long clientId;


    public Phone(String number, Long clientId) {
        this(null, number, clientId);
    }

    @PersistenceCreator
    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Long getClientId() {
        return clientId;
    }

    @Override
    public String toString() {

        return number;
        /*
        return "Client{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", clientId=" + clientId +
                '}';
                */
    }
}