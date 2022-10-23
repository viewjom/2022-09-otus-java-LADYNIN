package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class CustomerTest {

    @Test
    @DisplayName("Проверяем, анотации класса ClTest")
    public void Test1() throws  Exception {
        ClTest clTest;

        boolean before = false;
        boolean test = false;
        boolean after = false;
        byte count = 0;
        byte  cfail = 0;
        byte csuccess = 0;


        Class<ClTest> clazz = ClTest.class;

        System.out.println("Class Name:" + clazz.getSimpleName());

        Method[] methods = clazz.getDeclaredMethods();

        clTest = new ClTest(false,false, false);

        while (!after /*&& count < 10*/) {
            count ++;

        for (Method method : methods) {

            // System.out.println("Methods: " + method.getName());

         //   Annotation[] annotations = method.getDeclaredAnnotations();



                for (Annotation annotation : method.getDeclaredAnnotations()) {
                    System.out.println("Name: " + annotation.annotationType().getName());
                    //Start Method @Before
                    if (annotation.annotationType().getName().equals("ru.otus.Before")) {


                            before = (boolean) method.invoke(clTest);


                        System.out.println("ClTest Before " + before);

                    try {
                        csuccess++;
                        assertThat(before).isTrue();
                    } catch (final AssertionFailedError afe)
                        {
                            cfail++;

                        }


                    }

                    //Start Method @Test
                    if (annotation.annotationType().getName().equals("ru.otus.Test") ) {
                        test = (boolean) method.invoke(clTest);
                        System.out.println("ClTest Test " + test);

                        try {
                             csuccess++;
                             assertThat(test).isTrue();
                        } catch (final AssertionFailedError afe)
                        {
                            cfail++;

                        }
                    }

                    //Start Method @Afrter
                    if (annotation.annotationType().getName().equals("ru.otus.After")) {
                        after = (boolean) method.invoke(clTest);
                        System.out.println("ClTest After " + after);

                        try {
                            csuccess++;
                        assertThat(after).isTrue();
                        } catch (final AssertionFailedError afe)
                        {
                            cfail++;
                        }


                    }


                }

            }
            System.out.println("SUCCESS Количество Успешных тестов: " + csuccess);
            System.out.println("FAIL Количество НЕ Успешных тестов: " + cfail);
        }


    }



}
