package ru.otus.crm.service;

import java.io.IOException;
import java.util.Properties;

public class Utils {
    public String getKafkaAddress() throws IOException {
        Properties properties = new Properties ();
        String filename = "src/main/resources/properties.config";

        properties.load(this.getClass().getResourceAsStream("/properties.config"));

        return properties.getProperty("kafka-url") + ":" + properties.getProperty("kafka-port");
    }

}
