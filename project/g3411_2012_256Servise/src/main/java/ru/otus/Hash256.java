package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.service.JdbcImpl;
import ru.otus.kafka.consumer.MyConsumer;
import ru.otus.kafka.consumer.StringValueConsumer;

import java.sql.SQLException;

public class Hash256 {
    //private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String URL = "jdbc:postgresql://192.168.10.173:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

     private static JdbcImpl jdbc = new JdbcImpl();

    //private static DataTemplate dataTemplate;
    private static Long id;
    private static String vl;
    private static final Logger log = LoggerFactory.getLogger(Hash256.class);

    public static void main(String[] args)  {




        var consumer = new MyConsumer("192.168.10.173:9092");

        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);

        var dataConsumer = new StringValueConsumer(consumer, v ->{
            log.info("Get key:{}, value:{}", v.id(), v.value());
            try {

                jdbc.updateDocHash(dataSource.getConnection(), v.id(), v.value());

            } /*catch (NullPointerException e) {

        } */catch (SQLException e) {
                log.info("SQLException key:{}, value:{}", v.id(), v.value());
                throw new RuntimeException(e);
            }
        }


            /*{
            ;

            try {
                var connection = dataSource.getConnection();

                log.info("Update key:{}, value:{}", v.id(), v.value());
                var pst = connection.prepareStatement("update documents set hash = ? where id = ?");
                pst.setObject(1, v.value());
                pst.setObject(2, v.id());

                pst.execute();
                connection.commit();


            } catch (NullPointerException e) {

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }*/
        );
        consumer.getConsumer().commitAsync();
        dataConsumer.startConsume();

    }
}