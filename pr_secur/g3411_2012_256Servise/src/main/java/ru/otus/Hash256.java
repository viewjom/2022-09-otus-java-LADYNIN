package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.service.JdbcImpl;
import ru.otus.crm.service.ServiceGost3411;
import ru.otus.crm.service.Utils;
import ru.otus.kafka.consumer.MyConsumer;
import ru.otus.kafka.consumer.StringValueConsumer;

import java.io.IOException;
import java.sql.SQLException;

public class Hash256 {
    //private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static JdbcImpl jdbc = new JdbcImpl();

    //private static DataTemplate dataTemplate;
    private static Long id;
    private static String vl;
    private static final Logger log = LoggerFactory.getLogger(Hash256.class);



    public static void main(String[] args) throws SQLException, IOException {
         Utils utils = new Utils();

       // var consumer = new MyConsumer( "192.168.10.173:9092");
        var consumer = new MyConsumer( utils.getUrlKafka());

        var dataSource = new DriverManagerDataSource(utils.getUrlPostgresql(), USER, PASSWORD);
        ServiceGost3411 usageSample = new ServiceGost3411();
        var connection = dataSource.getConnection();

        var dataConsumer = new StringValueConsumer(consumer, v -> {
            log.info("Get key:{}, value:{}", v.id(), v.value());

            jdbc.updateDocHash(connection, v.id(), v.value());
        }
        );

        dataConsumer.startConsume();

    }
}