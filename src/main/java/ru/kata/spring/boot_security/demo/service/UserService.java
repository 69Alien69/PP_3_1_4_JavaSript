package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    void updateUser(User user);
    void removeUserById(long id);
    List<User> getUsers();
    User getUser(long id);
    User getUser(String username);
}
