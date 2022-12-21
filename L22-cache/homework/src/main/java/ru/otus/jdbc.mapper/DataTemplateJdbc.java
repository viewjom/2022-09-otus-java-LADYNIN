package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    private final DataTemplateJdbcReflection<T> dataTemplateJdbcReflection;
    private static final Logger logger = LoggerFactory.getLogger(DataTemplateJdbc.class);

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, DataTemplateJdbcReflection<T> dataTemplateJdbcReflection) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = (EntityClassMetaData<T>) entitySQLMetaData.getEntitySQLMetaData();


        this.dataTemplateJdbcReflection = dataTemplateJdbcReflection;
    }

    private static <T> T getInstance(EntityClassMetaData<T> entityClassMetaData) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<T> constructor = entityClassMetaData.getConstructor();
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for (int idx = 0; idx < params.length; idx++) {
            params[idx] = paramTypes[idx].isPrimitive() ? 0 : null;
        }
        return constructor.newInstance(params);

    }
    private static <T> void fillInstance(T instance, Field field, ResultSet resultSet) {
        final String fieldName = field.getName();

        try {
            field.setAccessible(true);
            field.set(instance, resultSet.getObject(fieldName));
        } catch (IllegalAccessException | SQLException e) {
            throw new  DataTemplateException(e);
        }
    }

   @Override
   public Optional<T> findById(Connection connection, long id) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
       var sql =  entitySQLMetaData.getSelectByIdSql();

       T instance = getInstance(entityClassMetaData);

       return dbExecutor.executeSelect(connection,  sql, List.of(id), rs -> {

                   try {
                       if (rs.next()) {
                           entityClassMetaData.getAllFields().forEach(s ->
                                   fillInstance(instance, s, rs)
                           );
                       }

                       return instance;
                   } catch (SQLException e) {
                       throw new DataTemplateException(e);
                   }
       });
   }

   @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();

        Object id;
        String sql = entitySQLMetaData.getInsertSql();
        List<Object> params = dataTemplateJdbcReflection.getFieldsValues(client, fields);

        id = dbExecutor.executeStatement(connection, sql, params);

        Field idField = entityClassMetaData.getIdField();

        if (idField != null) {
            try {
                idField.setAccessible(true);
                idField.set(client, id);
                return (long) id;
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return 0;
    }

    @Override
    public void update(Connection connection, T client) {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();

        String sql = entitySQLMetaData.getUpdateSql();
        List<Object> params;
        params = dataTemplateJdbcReflection.getFieldsValues(client, fields);

        Object id = dataTemplateJdbcReflection.getIdValue(client);
        params.add(id);
        dbExecutor.executeStatement(connection, sql, params);

    }

}