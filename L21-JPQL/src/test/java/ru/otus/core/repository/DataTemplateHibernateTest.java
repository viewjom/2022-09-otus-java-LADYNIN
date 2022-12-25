package ru.otus.core.repository;

import junit.framework.AssertionFailedError;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.base.AbstractHibernateTest;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DataTemplateHibernateTest extends AbstractHibernateTest {
    private StandardServiceRegistry serviceRegistry;
    private Metadata metadata;
    private SessionFactory sessionFactory;


    @BeforeEach
    public void setUp() {
        makeTestDependencies();
    }
    @AfterEach
    public void tearDown() {
        sessionFactory.close();
    }


    @Test
    @DisplayName(" корректно сохраняет, изменяет и загружает клиента по заданному id")
    void shouldSaveAndFindCorrectClientById() {
        //given
        var client = new Client("Вася");

        // Это надо раскомментировать, у выполненного ДЗ, все тесты должны проходить
        // Кроме удаления комментирования, тестовый класс менять нельзя
/*
        var client = new Client(null, "Vasya", new Address(null, "AnyStreet"), List.of(new Phone(null, "13-555-22"),
                new Phone(null, "14-666-333")));
*/

        //when
        var savedClient = transactionManager.doInTransaction(session -> {
            clientTemplate.insert(session, client);
            return client;
        });

        //then
        assertThat(savedClient.getId()).isNotNull();
        assertThat(savedClient.getName()).isEqualTo(client.getName());

        //when
        var loadedSavedClient = transactionManager.doInReadOnlyTransaction(session ->{
                    var res = clientTemplate.findById(session, savedClient.getId())
                            .orElseThrow(() -> new AssertionFailedError("expected: not <null>"));
                    return Optional.ofNullable(res.clone());
                }
        );

        //then
        assertThat(loadedSavedClient).isPresent().get()
                .usingRecursiveComparison()
                .isEqualTo(savedClient);

        //when
        var updatedClient = savedClient.clone();
        updatedClient.setName("updatedName");
        transactionManager.doInTransaction(session -> {
            clientTemplate.update(session, updatedClient);
            return null;
        });

        //then
        var loadedClient = transactionManager.doInReadOnlyTransaction(session -> {
                    var res = clientTemplate.findById(session, updatedClient.getId())
                            .orElseThrow(() -> new AssertionFailedError("expected: not <null>"));

                    return Optional.of(res.clone());
                }
        );
        assertThat(loadedClient).isPresent().get().usingRecursiveComparison().isEqualTo(updatedClient);

        //when
        var clientList = transactionManager.doInReadOnlyTransaction(session ->
                clientTemplate.findAll(session).stream()
                        .map(Client::clone).collect(Collectors.toList())
        );

        //then
        assertThat(clientList.size()).isEqualTo(1);
        assertThat(clientList.get(0))
                .usingRecursiveComparison()
                .isEqualTo(updatedClient);


        //when
        clientList = transactionManager.doInReadOnlyTransaction(session ->
                clientTemplate.findByEntityField(session, "name", "updatedName")
                        .stream().map(Client::clone).collect(Collectors.toList())
        );

        //then
        assertThat(clientList.size()).isEqualTo(1);
        assertThat(clientList.get(0))
                .usingRecursiveComparison()
                .isEqualTo(updatedClient);
    }

    private void makeTestDependencies() {
        var cfg = new Configuration();
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        cfg.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        cfg.setProperty("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        cfg.setProperty("hibernate.connection.username", "sa");
        cfg.setProperty("hibernate.connection.password", "");
        cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.format_sql", "false");
        cfg.setProperty("hibernate.generate_statistics", "true");
        cfg.setProperty("hibernate.hbm2ddl.auto", "create");
        cfg.setProperty("hibernate.enable_lazy_load_no_trans", "false");
        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties()).build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(Phone.class);
        metadataSources.addAnnotatedClass(Address.class);
        metadataSources.addAnnotatedClass(Client.class);
        metadata = metadataSources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }
}