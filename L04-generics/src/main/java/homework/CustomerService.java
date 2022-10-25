package homework;


import java.util.*;

@SuppressWarnings("unchecked")
public class CustomerService  {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private Customer customerH;
    private String dataH;

    TreeMap<Customer, String> ttmap = new TreeMap<>(Comparator.comparingInt(o -> (int) o.getScores()));

    public Map.Entry<Customer, String> getSmallest() {

        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        return  ttmap.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {

        return ttmap.higherEntry(customer);
    }

    public void add(Customer customer, String data) {

        ttmap.put(customer, data);

    }


}