package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import ru.otus.repository.ClientRepository;

@Service
public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    @Autowired

    private JdbcTemplate jdbcTemplateObject;

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;
    public DbServiceClientImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
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
    public byte[] loadFile2(Long id) {

        var clientOptional = clientRepository.findById(id);

        return clientOptional.get().getFile();
    }

    @Override
    public void updateDocument(Long id, String hash) {

        String SQL = "update documents set hash512 = ? where id = ?";

        jdbcTemplateObject.update(SQL, hash, id);

        log.info("updateDocument Id:{} ", id);
    }

    @Override
    public void updateDocument2(Long id, String hash) {
        var clientOptional = clientRepository.findById(id);

        var doc = clientOptional.get();
        doc.setHash512(hash);

        clientRepository.save(doc);

        log.info("updateDocument Id:{} ", id);
    }
}