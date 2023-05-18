package ru.otus.auth.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.auth.entity.User;
import ru.otus.auth.repository.UserRepo;
import ru.otus.auth.service.UserService;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void addUser(User user) {
        userRepo.addUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteUser(id);
    }

    @Override
    public void editUser(User user) {
        userRepo.editUser(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.getUserByUsername(username);
    }
}
