package ru.otus.crm.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
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

/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "client_id")
    private List<Phone> phones;


 */
    public Client() {
    }

    public Client(Long id, String name,Address address, List<Phone> phones) {
     /*   if (phones == null) {
            throw new IllegalArgumentException("phones is null");
        }
*/
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
    public Address getAddress() {
        return address;
    }

    public void setAddressDataSet(Address addressDataSet) {
        this.address = addressDataSet;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void addPhone(Phone phone) {
        phone.setClient(this);
        phones.add(phone);
    }

    public void removePhone(Phone phone) {
        phone.setClient(null);
        phones.remove(phone);
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
                && Objects.equals(name, client.name)/*
                && Objects.equals(address, client.address)
                && equalsPhones(client.phones)*/;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name/*, address, phones*/);
    }

    private boolean equalsPhones(List<Phone> otherPhones) {
        if (phones == otherPhones) {
            return true;
        }
        if(phones == null || otherPhones == null || (phones.size() != otherPhones.size()) ) {
            return false;
        }
        List<Phone> thisCopy = new ArrayList<>(phones);
        List<Phone> otherCopy = new ArrayList<>(otherPhones);
        thisCopy.sort(Comparator.comparing(Phone::getNumber));
        otherCopy.sort(Comparator.comparing(Phone::getNumber));
        return thisCopy.equals(otherCopy);
    }
}
