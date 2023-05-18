package ru.otus.auth.repository;


import org.springframework.data.repository.CrudRepository;
import ru.otus.auth.entity.User;

import java.util.List;

public interface UserRepo {
    void addUser(User user);

    void deleteUser(Long id);

    void editUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    User getUserByUsername(String username);

}