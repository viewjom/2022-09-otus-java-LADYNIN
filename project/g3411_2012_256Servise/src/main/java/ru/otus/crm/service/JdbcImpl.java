package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.gost.UsageSample;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcImpl implements Jdbc {
    private static final Logger log = LoggerFactory.getLogger(JdbcImpl.class);
    UsageSample usageSample = new UsageSample();

    @Override
    public byte[] getFile(Connection connection, Long id) {
        try {

            var pst = connection.prepareStatement("select file from documents where id = ?");
            pst.setObject(1, id);

            ResultSet resultSet = pst.executeQuery();

            resultSet.next();
            return resultSet.getBytes(1);

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDoc(Connection connection, Long id, String value) {
        try {

            log.info("Update key:{}, value:{}", id, value);
            var pst = connection.prepareStatement("update documents set hash256 = ? where id = ?");
            pst.setObject(1, value);
            pst.setObject(2, id);

            pst.execute();
            connection.commit();

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDocHash(Connection connection, Long id, String value) {

        try {
            var hash = usageSample.getHex(getFile(connection, id), value);

            updateDoc(connection, id, hash);

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }
}
