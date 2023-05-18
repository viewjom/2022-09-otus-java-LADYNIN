package ru.otus.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.otus.auth.entity.User;
import java.util.List;


public interface UserService extends UserDetailsService {
    void addUser(User user);

    void deleteUser(Long id);

    void editUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();
}