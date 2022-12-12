package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public final class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private String cacheSelectByIdSql;
    private String cacheInsertSql;
    private String cacheUpdateSql;
    private final EntityClassMetaData<?> entityClassMetaDataClient;


    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaDataClient) {
        this.entityClassMetaDataClient = entityClassMetaDataClient;
    }
    @Override
    public EntityClassMetaData<?>  getEntitySQLMetaData(){
        return entityClassMetaDataClient;
    }

    @Override
    public String getSelectAllSql() {

        return "SELECT * FROM " + entityClassMetaDataClient.getName().toLowerCase();

    }

    @Override
    public String getSelectByIdSql() {

        if (cacheSelectByIdSql != null) {
            return cacheSelectByIdSql;
        }
        Field idField = entityClassMetaDataClient.getIdField();
        if (idField == null) {
            cacheSelectByIdSql = getSelectAllSql();
        }
        else {
            String request = String.format("SELECT * FROM %s WHERE %s = ?",
                    entityClassMetaDataClient.getName().toLowerCase(),
                    idField.getName().toLowerCase()
            );
            cacheSelectByIdSql = request.toString();
        }
        return cacheSelectByIdSql;
    }

    @Override
    public String getInsertSql() {
     /*   return String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                tableName, String.join(", ", fields), getFieldsByTemplate(fields, "?")
        );*/
        if (cacheInsertSql != null) {
            return cacheInsertSql;
        }
        List<Field> fields = entityClassMetaDataClient.getFieldsWithoutId();
        StringBuilder request = new StringBuilder();
        StringBuilder values = new StringBuilder();

        request.append("INSERT INTO ").append(entityClassMetaDataClient.getName().toLowerCase()).append(" (");
        for (Field field : fields) {
            request.append(field.getName()).append(",");
            values.append("?,");
        }
        values.deleteCharAt(values.length() - 1);
        values.append(')');
        request.deleteCharAt(request.length() - 1);
        request.append(") VALUES (").append(values);
        return cacheInsertSql = request.toString();
    }

    @Override
    public String getUpdateSql() {
      /*  return String.format(
                "UPDATE %s SET %s WHERE %s = ?",
                tableName, getFieldsByTemplate(fields, FIELD_MASK + " = ?"), fieldNameId
        );
    }*/
        if (cacheUpdateSql != null) {
            return cacheUpdateSql;
        }
        Field idField = entityClassMetaDataClient.getIdField();
        if (idField == null) {
            cacheUpdateSql = "";
        }
        else {
            List<Field> fields = entityClassMetaDataClient.getFieldsWithoutId();
            StringBuilder request = new StringBuilder();

            request.append("UPDATE ").append(entityClassMetaDataClient.getName().toLowerCase()).append(" SET ");
            for (Field field : fields) {
                request.append(field.getName()).append(" = ?,");
            }
            request.deleteCharAt(request.length() - 1)
                    .append(" WHERE ")
                    .append(idField.getName().toLowerCase())
                    .append(" = ?");
            cacheUpdateSql = request.toString();
        }
        return cacheUpdateSql;
    }


}