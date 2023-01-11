package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.cfg.Configuration;
import ru.otus.client.repository.DataTemplateHibernate;
import ru.otus.client.repository.HibernateUtils;
import ru.otus.client.service.DbServiceClientImpl;
import ru.otus.client.sessionmanager.TransactionManagerHibernate;
import ru.otus.flyway.MigrationsExecutorFlyway;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.server.ClientsWebServer;
import ru.otus.server.ClientsWebServerWithBasicSecurity;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

/*
    Полезные для демо ссылки
    // Стартовая страница
    http://localhost:8080
    // Страница пользователей
    http://localhost:8080/users
    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithBasicSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";

    private static DbServiceClientImpl dbServiceClient;
    private static Metadata metadata;

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";


    public static void main(String[] args) throws Exception {

        DatabaseSetup();

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
        ClientsWebServer clientsWebServer = new ClientsWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, dbServiceClient, gson, templateProcessor);

        clientsWebServer.start();
        clientsWebServer.join();
    }

    private static void DatabaseSetup() {

        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");
        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

    }
}