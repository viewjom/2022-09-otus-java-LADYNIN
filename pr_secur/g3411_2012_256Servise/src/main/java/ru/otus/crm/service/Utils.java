package ru.otus.crm.service;

import java.io.IOException;
import java.util.Properties;

public class Utils {
    private Properties properties = new Properties();

    public Utils() throws IOException {
        properties.load(this.getClass().getResourceAsStream("/properties.config"));
    }

    public String getUrlKafka() throws IOException {


        //properties.load(this.getClass().getResourceAsStream("/properties.config"));

        return properties.getProperty("kafka-url") + ":" + properties.getProperty("kafka-port");
    }

    public String getUrlPostgresql() throws IOException {
       // Properties properties = new Properties();

        //properties.load(this.getClass().getResourceAsStream("/properties.config"));

        return "jdbc:postgresql://"
                + properties.getProperty("postgresql-url")
                + ":" + properties.getProperty("postgresql-port")
                + "/" + properties.getProperty("postgresql-db");
    }

}
