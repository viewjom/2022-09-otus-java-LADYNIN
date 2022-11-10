package ru.otus.calculator;

import ru.otus.proxy.Log;

public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public void calculation(int param1) {
        System.out.println("1: Class TestLogging, calculation(int param1)");
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("2: Class TestLogging, calculation(int param1, int param2)");
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("3: Class TestLogging,  calculation(int param1, int param2, String param3)");
    }
}