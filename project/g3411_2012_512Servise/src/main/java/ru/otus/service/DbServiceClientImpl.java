package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    @Autowired

    private JdbcTemplate jdbcTemplateObject;

    public DbServiceClientImpl() {

    }

    @Override

    public byte[] loadFile(Long id) {
        try {
            String SQL = "select file from documents where id = ?";

            byte[] file = jdbcTemplateObject.queryForObject(SQL, byte[].class, new Object[]{id});

            return file;

        } catch (EmptyResultDataAccessException e) {
            log.debug("No record found in database for id{}", id);
        }

        return null;
    }

    @Override
    public void updateDocument(Long id, String hash) {

        String SQL = "update documents set hash512 = ? where id = ?";

        jdbcTemplateObject.update(SQL, hash, id);

        log.info("updateDocument Id:{} ", id);
    }
}