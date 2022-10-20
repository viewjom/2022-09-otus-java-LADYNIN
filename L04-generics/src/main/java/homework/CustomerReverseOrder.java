package homework;


import java.util.LinkedList;
public class CustomerReverseOrder {
    private Customer customerH;
    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    LinkedList<Customer> ll = new LinkedList<>();
    public void add(Customer customer) {
      ll.add(customer);
    }

    public Customer take() {
        customerH = ll.pollLast();
        return customerH;
    }
}