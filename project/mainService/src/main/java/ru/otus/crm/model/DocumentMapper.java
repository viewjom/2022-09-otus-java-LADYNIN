package ru.otus.crm.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentMapper implements RowMapper<Document> {
    @Override
    public Document mapRow(ResultSet rs, int rowNum) throws SQLException {
        Document document  = new Document(null,null,null,null,null);

        document.setId(rs.getLong("id"));
        document.setFileName(rs.getString("file_name"));
        document.setFile(rs.getBytes("file"));
        document.setHash512(rs.getString("hash512"));
        document.setHash256(rs.getString("hash256"));
        document.setClientId(rs.getLong("client_id"));

        return document;
    }
}
