package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    void saveUser(User user);
    void updateUser(User user);
    void removeUserById(long id);
    List<User> getUsers();
    User getUser(long id);
    Optional<User> getUserByUsername(String username);
}
