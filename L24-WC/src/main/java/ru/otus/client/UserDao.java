package ru.otus.client;

import ru.otus.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);
    Optional<User> findRandomUser();

    Optional<User> findByLogin(String login);
    //  Optional<User> findByLogin(String login);

    Map<String, Object> getAll();

}