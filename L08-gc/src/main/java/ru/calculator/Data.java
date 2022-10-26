package ru.calculator;

public class Data {
    private final Integer value;

    public Data(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /*
    @Override
    public boolean equals(Object o) {

        //  if (this == o) return true;
        if (  o == null || this.getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (this.value != data.value) return false;

        //  return name != null ? name.equals(customer.name) : customer.name == null;

        return value == data.value;

    }

    @Override
    public int hashCode() {
        Integer result = (Integer) (value ^ (value >>> 32));

        //  result = 31 * result + (name != null ? name.hashCode() : 0);
        // result = 31 * result + (int) (scores ^ (scores >>> 32));
        return result;


    }
*/
}