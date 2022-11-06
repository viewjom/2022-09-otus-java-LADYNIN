package ru.otus.proxy;

import ru.otus.calculator.TestLoggingInterface;

public interface MyClassInterface extends TestLoggingInterface {

    void secureAccess(String param);

    @Override
    void calculation(int param1);

    @Override
    void calculation(int param1, int param2);

    @Override
    void calculation(int param1, int param2, String param3);
}