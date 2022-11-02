package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.lang.annotation.Annotation;

import java.util.function.Predicate;

public class Runner {

    private Method beforeMethod = null;
    private Method afterMethod = null;
    private Record r = new Record();

    //@Override
    void run(final String className) throws ClassNotFoundException {

        final Class<?> clazz =  Class.forName(className);
        final Method[] methods = Objects.requireNonNull(clazz).getMethods();

        //Test.class
        final Predicate<Method> predTest = (method) -> method.isAnnotationPresent(Test.class);
        final Method[] testMethods = Arrays.stream(methods).filter(predTest).toArray(Method[]::new);
        //Before.class
        final Predicate<Method> predBefore = (method) -> method.isAnnotationPresent(Before.class);
        final Method[] beforeMethods = Arrays.stream(methods).filter(predBefore).toArray(Method[]::new);


        //After.class
        final Predicate<Method> predAfter = (method) -> method.isAnnotationPresent(After.class);
        final Method[] afterMethods = Arrays.stream(methods).filter(predAfter).toArray(Method[]::new);

        if (beforeMethods[0] != null){
            beforeMethod = beforeMethods[0];;
        }

        if (afterMethods[0] != null){
            afterMethod = afterMethods[0];
        }

        for (Method testMethod : testMethods) {
            executeTest(clazz, beforeMethod, afterMethod, testMethod);
        }

        final String result = String.format("executed: %s, passed: %s, failed: %s", r.getExecuted(), r.getPassed(), r.getFailed());

        System.out.println("All tests are finished");
        System.out.println(result);
    }

    private <T> void executeTest(final Class<T> clazz, final Method beforeMethod, final Method afterMethod, final Method testMethod) {

        try {
            final T testObject = clazz.getDeclaredConstructor().newInstance();

            if (!Objects.isNull(beforeMethod)) {
                beforeMethod.invoke(testObject);
            }

            testMethod.invoke(testObject);


            if (!Objects.isNull(afterMethod)) {
                afterMethod.invoke(testObject);
            }
            r.addPassed();

        } catch (Exception e) {
            e.printStackTrace();
            r.addFailed();

        } finally {
            r.addExecuted();

        }
    }
}