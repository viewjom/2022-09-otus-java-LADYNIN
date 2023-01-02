package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.appcontainer.exception.ComponentsWithSameName;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        checkConfigClass(configClass);
        // You code here...


        final Object instance = configClass.getDeclaredConstructor().newInstance();


        final List<Method> methods = Stream.of(configClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparing(m -> m.getAnnotation(AppComponent.class).order()))
                .collect(Collectors.toList());

        for (Method method : methods) {

            final String bean = method.getDeclaredAnnotation(AppComponent.class).name();

            if (appComponentsByName.get(bean) != null) {
                throw new ComponentsWithSameName();
            }

            final Object[] args =
                    Arrays.stream(method.getParameterTypes())
                            .map(s -> {
                                try {

                                    return getAppComponent(s);
                                } catch (ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .toArray();

            try {
                final Object executionResult = method.invoke(instance, args);
                appComponentsByName.put(bean, executionResult);
                appComponents.add(executionResult);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) throws ClassNotFoundException {

        var count = 0;
        Object o = null;

        for (Object obj : appComponents) {
            if (componentClass.isAssignableFrom(obj.getClass())) {
                count++;
                o = obj;
            }
        }

        if (count == 1) {

            return (C) o;
        } else {

            throw new IllegalStateException(String.format("err component %s", componentClass.getName()));
        }
    }

            /*

        return (C)appComponents.stream()
                .filter(c -> componentClass.isAssignableFrom(c.getClass()))
                .filter(Collections.frequency(, ) ==1) //????
                .findAny()
                .orElseThrow(() -> new ClassNotFoundException(componentClass.getName()));
  */


    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
        //return null;
    }

}