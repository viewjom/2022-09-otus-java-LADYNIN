package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.jdbc.mapper.DataTemplateJdbc;
import ru.otus.jdbc.mapper.EntitySQLMetaDataImpl;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.DataTemplateJdbcReflection;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class HomeWork {
    //private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String URL = "jdbc:postgresql://192.168.10.173:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);


    public static void main(String[] args) {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();
        Map<Integer, Client> map = new HashMap<>();
// Работа с клиентом
        EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);

        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);


        var dataTemplateClient = new DataTemplateJdbc<Client>(dbExecutor,
                entitySQLMetaDataClient,
                new DataTemplateJdbcReflection<>(entityClassMetaDataClient));

        MyCache<String, Client> cache = new MyCache<>();
        //реализация DataTemplate, универсальная

// Код дальше должен остаться

        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient, cache);

        //dbServiceClient.saveClient(new Client("dbServiceFirst"));


        for(int i = 0; i < 2; i++) {
        //    var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
            map.put(i,dbServiceClient.saveClient(new Client("dbServiceSecond"+i)));
        }


        for(int i = 0; i < 2; i++) {
        var cl = map.get(i);

        var clientSecondSelected = dbServiceClient.getClient(cl.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + cl.getId()));



        }


     //   log.info("clientSecondSelected:{}", clientSecondSelected);

// Сделайте тоже самое с классом Manager (для него надо сделать свою таблицу)

 /*       EntityClassMetaData entityClassMetaDataManager = new EntityClassMetaDataImpl<>(Manager.class);

        EntitySQLMetaData entitySQLMetaDataManager = new EntitySQLMetaDataImpl(entityClassMetaDataManager);

        var dataTemplateManager = new DataTemplateJdbc<Manager>(dbExecutor,
                entitySQLMetaDataManager,
                new DataTemplateJdbcReflection<>(entityClassMetaDataManager));

        var dbServiceManager = new DbServiceManagerImpl(transactionRunner, dataTemplateManager);
        dbServiceManager.saveManager(new Manager("ManagerFirst"));

       var managerSecond = dbServiceManager.saveManager(new Manager("ManagerSecond"));

        var managerSecondSelected = dbServiceManager.getManager(managerSecond.getNo())
                .orElseThrow(() -> new RuntimeException("Manager not found, id:" + managerSecond.getNo()));


        log.info("managerSecondSelected:{}", managerSecondSelected);
*/
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}