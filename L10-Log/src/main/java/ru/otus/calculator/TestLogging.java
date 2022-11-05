package ru.otus.calculator;

import ru.otus.proxy.Log;

public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public void calculation(int param1) {
        System.out.println("\tClass TestLoggingInterface, Method: calculation(int param1)");
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("\tClass TestLoggingInterface, Method: calculation(int param1, int param2)");
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("\tClass TestLoggingInterface, Method: calculation(int param1, int param2, String param3)");
    }
}