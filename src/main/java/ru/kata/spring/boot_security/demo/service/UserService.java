package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserGetDTO;
import ru.kata.spring.boot_security.demo.dto.UserPostDTO;
import ru.kata.spring.boot_security.demo.dto.UserPutDTO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    void saveUser(UserPostDTO userPostDTO);
    void updateUser(User user);
    void updateUserWithOldPassword(User user);
    void updateUser(UserPutDTO userPutDTO);
    void removeUserById(long id);
    List<User> getUsers();
    User getUser(long id);
    List<UserGetDTO> getUsersGetDTO();
    UserGetDTO getUserGetDTO(long id);
}
