package ru.otus.calculator;

import ru.otus.proxy.Log;

public class TestLogging2 implements TestLoggingInterface {


    @Override
    public void calculation(int param1) {
        System.out.println("1: Class TestLogging2, calculation(int param1)");
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        System.out.println("2: Class TestLogging2, calculation(int param1, int param2)");
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("3: Class TestLogging2,  calculation(int param1, int param2, String param3)");
    }
}