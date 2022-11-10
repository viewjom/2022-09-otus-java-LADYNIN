package ru.otus;

import ru.otus.calculator.TestLogging;
import ru.otus.calculator.TestLogging2;
import ru.otus.calculator.TestLoggingInterface;

public class Main {
    public static void main(String[] args) {


        System.out.println("----ТЕСТ1----");
        TestLoggingInterface anyClass = Ioc.createMyClass(new TestLogging());
        anyClass.calculation(1000);
        anyClass.calculation(2000, 3000);
        anyClass.calculation(4000, 5000, "TEST");
        System.out.println("----ТЕСТ2----");
        TestLoggingInterface anyClass2 = Ioc.createMyClass(new TestLogging2());
        anyClass2.calculation(11011);
        anyClass2.calculation(12012, 13013);
        anyClass2.calculation(14014, 15015, "BEST");
    }
}