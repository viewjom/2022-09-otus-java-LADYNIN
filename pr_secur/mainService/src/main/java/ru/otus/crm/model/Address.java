package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;


@Table("addresses")
public class Address {

    @Id
    private final Long id;

    private final int zip;
    private final String street;
    private final String house;
    private final String guid;
    private final Long clientId;
    public Address(int zip, String street, String house,String guid, Long clientId) {
        this(null, zip, street, house, guid, clientId);
    }

    @PersistenceCreator
    public Address(Long id, int zip, String street, String house,String guid, Long clientId) {
        this.id = id;
        this.zip = zip;
        this.street = street;
        this.house = house;
        this.guid = guid;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }
    public int getZip() {
        return zip;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }
    public String getGuid() {
        return guid;
    }
    public Long getClientId() {
        return clientId;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}