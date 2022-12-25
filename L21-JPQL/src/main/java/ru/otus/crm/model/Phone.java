package ru.otus.crm.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "number")
    private String number;

    @ManyToOne
    private Client client;

    public Phone() {
    }

    public Phone(long id, String number, Client client) {
        this.id = id;
        this.number = number;
        this.client = client;
    }

    public Phone(long id, String number) {
        this.id = id;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;
        Phone that = (Phone) o;
        return id == that.id && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }
}
/*
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "phones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private String number;
}
*/