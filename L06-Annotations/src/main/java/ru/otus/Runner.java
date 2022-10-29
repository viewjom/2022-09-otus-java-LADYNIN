package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

public class Runner {

    private int executed;
    private int passed;
    private int failed;

    //@Override
    public void run(final String className) throws ClassNotFoundException {
      //  final Class<?> clazz = getClassForName(className);
        final Class<?> clazz =  Class.forName(className);
        final Method[] methods = Objects.requireNonNull(clazz).getMethods();

        //Test.class
        final Predicate<Method> predTest = (method) -> method.isAnnotationPresent(Test.class);
        final Method[] testMethods = Arrays.stream(methods).filter(predTest).toArray(Method[]::new);
        //Before.class
        final Predicate<Method> predBefore = (method) -> method.isAnnotationPresent(Before.class);
        final Method[] beforeMethods = Arrays.stream(methods).filter(predBefore).toArray(Method[]::new);
        Method beforeMethod;

        //Before.class
        final Predicate<Method> predAfter = (method) -> method.isAnnotationPresent(After.class);
        final Method[] afterMethods = Arrays.stream(methods).filter(predAfter).toArray(Method[]::new);
        Method afterMethod;

         try{
          beforeMethod = beforeMethods[0];
            } catch (ArrayIndexOutOfBoundsException e) {
             beforeMethod = null;
         }


        try{
             afterMethod = afterMethods[0];
         } catch (ArrayIndexOutOfBoundsException e) {
             afterMethod = null;
         }




        for (Method testMethod : testMethods) {
            executeTest(clazz, beforeMethod, afterMethod, testMethod);
        }

        final String result = String.format("executed: %s, passed: %s, failed: %s", executed, passed, failed);

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

            if (!Objects.isNull(beforeMethod)) {
                afterMethod.invoke(testObject);
            }

            passed++;
        } catch (Exception e) {
            e.printStackTrace();
            failed++;
        } finally {
            executed++;
        }
    }
}