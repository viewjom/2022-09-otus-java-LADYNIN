package ru.otus;

import ru.otus.calculator.TestLogging;
import ru.otus.calculator.TestLoggingInterface;
import ru.otus.proxy.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;

public class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());


        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myClass;
        private final List<Method> logMethods;



        DemoInvocationHandler(TestLoggingInterface myClass) {
            this.myClass = myClass;
            this.logMethods = Arrays.stream(myClass.getClass().getMethods())
                    .filter((lmethod) -> lmethod.isAnnotationPresent(Log.class))
                    .collect(Collectors.toList());

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            for (Method logMethod : logMethods) {
                if (method.getName().equals(logMethod.getName())
                        && Arrays.equals(method.getParameterTypes(), logMethod.getParameterTypes())) {

                    System.out.println("executed method:" + logMethod.getName() + ", params: ");
                    for (Object s : args) {
                        System.out.println(s);
                    }
                }
            }

            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}