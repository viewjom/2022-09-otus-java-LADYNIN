package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("clients")
public class Client {

    @Id
    private final Long id;

    private final String name;

    @MappedCollection(idColumn = "client_id")
    private final Address address;
    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phone;

    @MappedCollection(idColumn = "client_id")
    private final Set<Document> document;
    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phone, Set<Document> document) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Set<Phone> getPhone() {
        return phone;
    }

    public Set<Document> getDocument() {
        return document;
    }

    @Override
    public String toString() {

        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone + '\'' +
                ", document=" + document +
                '}';
    }
}