package ru.otus.json.model;


//Допустим, этот класс библиотечный, его нельзя менять
public final class Measurement {
    private final String result;


    public Measurement(String result) {
        this.result = result;

    }

    public String getName() {
        return result;
    }


    @Override
    public String toString() {
        return "Measurement{" +
                "result='" + result + // '\'' +
                //", value=" + value +
                '}';
    }
}