package ru.otus.crm.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Phone> phones;

    public Client() {
    }

    public Client(Long id, String name,Address address, List<Phone> phones) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        phones.stream().forEach(s->s.setClient(this));

    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phones);
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id
                && Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


}
