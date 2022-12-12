package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

  /*  public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData,
                            DataTemplateJdbcReflection<T> dataTemplateJdbcReflection) {

        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
        this.dataTemplateJdbcReflection =  dataTemplateJdbcReflection;
    }

   */

   /* @Override
    public Optional<T> findById(Connection connection, long id) {
        Optional<T> optObject = Optional.empty();
        try {
            String sql = entitySQLMetaData.getSelectByIdSql();
            optObject = (Optional<T>) dbExecutor.executeSelect(connection, sql, id, this::apply);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return optObject.orElse(null);
      //  throw new UnsupportedOperationException();
    }*/
   @Override
   public Optional<T> findById(Connection connection, long id) {
      // Optional<T> optObject = Optional.empty();
       return (Optional<T>) dbExecutor.executeSelect(connection, "select id, name from client where id  = ?", List.of(id), rs -> {
           try {
               if (rs.next()) {
                   return new Client(rs.getLong("id"), rs.getString("name"));
               }
               return null;
           } catch (SQLException e) {
               throw new DataTemplateException(e);
           }
       });
   }


 /*   @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
    }*/

    @Override
    public List<T> findAll(Connection connection) {
        return (List<T>) dbExecutor.executeSelect(connection, "select * from client", Collections.emptyList(), rs -> {
            var clientList = new ArrayList<Client>();
            try {
                while (rs.next()) {
                    clientList.add(new Client(rs.getLong("id"), rs.getString("name")));
                }
                return clientList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }


    @Override
    public long insert(Connection connection, T client) {


        try {
            System.out.println("3-----------------------------");
            List<Field> fields = entityClassMetaData.getFieldsWithoutId();
            System.out.println("34-----------------------------");
            List<Object> params = dataTemplateJdbcReflection.getFieldsValues(client, fields);
            System.out.println("35-----------------------------");
            return dbExecutor.executeStatement(connection, "insert into client(name) values (?)",
                    params);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
 /*   @Override
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
                return 1;
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return 0;
    }
*/
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
  /*
 public void update(Connection connection, T client) {
     try {
         List<Field> fields = entityClassMetaData.getFieldsWithoutId();
         List<Object> params;
         params = dataTemplateJdbcReflection.getFieldsValues(client, fields);
         Object id = dataTemplateJdbcReflection.getIdValue(client);
         params.add(id);
         dbExecutor.executeStatement(connection, "update client set name = ? where id = ?",
                 params);
     } catch (Exception e) {
         throw new DataTemplateException(e);
     }
 }

  */


}