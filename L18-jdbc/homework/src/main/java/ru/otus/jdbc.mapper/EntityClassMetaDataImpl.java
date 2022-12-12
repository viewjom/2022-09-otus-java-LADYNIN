package ru.otus.jdbc.mapper;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ru.otus.core.sessionmanager.DataBaseOperationException;
import ru.otus.crm.model.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private static final Predicate<Field> FIELD_ID_PREDICATE = field -> field.isAnnotationPresent(Id.class);

    private final Class<T> clazz;
    private List<Field> cacheAllFields;
    private Constructor<T> cacheConstructor;
    private boolean idFieldIsPresented;
    private Field cacheIdField;
    private List<Field> cacheFieldsWithoutId;
    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        if (cacheConstructor == null) {
            cacheConstructor = (Constructor<T>) clazz.getConstructors()[0];
        }
        return cacheConstructor;
    }

    @Override
    public Field getIdField() {
        if (idFieldIsPresented) {
            return cacheIdField;
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idFieldIsPresented = true;
                return cacheIdField  = field;
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {

        if (cacheAllFields == null) {
            cacheAllFields = List.of(clazz.getDeclaredFields());
        }
        return cacheAllFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {

        System.out.println("5-----------------------------");
        if (cacheFieldsWithoutId != null) {
            return cacheFieldsWithoutId;
        }
        cacheFieldsWithoutId = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Id.class)) {
                cacheFieldsWithoutId.add(field);
            }
        }
        return cacheFieldsWithoutId;
    }
  /*  public List<Field> getFieldsWithoutId() {
        return getAllFields().stream()
                .filter(FIELD_ID_PREDICATE.negate())
                .collect(Collectors.toList());
    }

   */



}
