package homework;

import java.util.Objects;

public class Customer {
    private final long id;
    private String  name;
    private  final long scores;

    //todo: 1. в этом классе надо исправить ошибки

    public  Customer(long id, String name, long scores) {


            this.id = id;
            this.name = name;
            this.scores = scores;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getScores() {
        return scores;
    }

    public final  void setName(String name) {

        if(Objects.equals(name, "updatedName"))
        this.name = name;
    }

    public void setScores(long scores) {
        //  if(scores <= 888)
       // this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                '}';
    }

    @Override
    public boolean equals(Object o) {

      //  if (this == o) return true;
        if (  o == null || this.getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

         if (this.id != customer.id) return false;

      //  return name != null ? name.equals(customer.name) : customer.name == null;

        return id == customer.id;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));

      //  result = 31 * result + (name != null ? name.hashCode() : 0);
       // result = 31 * result + (int) (scores ^ (scores >>> 32));
        return result;


    }


}