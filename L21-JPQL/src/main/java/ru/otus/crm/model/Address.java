package ru.otus.crm.model;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "street")
    private String street;

    public Address() {
    }

    public Address(long id, String street) {
        this.id = id;
        this.street = street;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address that = (Address) o;
        return id == that.id && Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street);
    }
}

/*
@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;
}
*/