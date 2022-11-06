package ru.otus;

import ru.otus.calculator.TestLoggingInterface;

public class Main  {
    public static void main(String[] args) {

        TestLoggingInterface anyClass = Ioc.createMyClass();
        anyClass.calculation(1000);
        anyClass.calculation(2000,3000);
        anyClass.calculation(4000,5000,"TEST");
    }
}