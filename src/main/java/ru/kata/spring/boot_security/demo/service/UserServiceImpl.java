package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.dto.UserGetDTO;
import ru.kata.spring.boot_security.demo.dto.UserPostDTO;
import ru.kata.spring.boot_security.demo.dto.UserPutDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void updateUserWithOldPassword(User user) {
        userDAO.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        userDAO.removeUserById(id);
    }

    @Override
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    @Override
    public User getUser(long id) {
        return userDAO.getUser(id);
    }

    @Override
    public List<UserGetDTO> getUsersGetDTO() {
        List<User> users = getUsers();
        ArrayList<UserGetDTO> userTableDTOS = new ArrayList<>();
        for (User user : users) {
            userTableDTOS.add(new UserGetDTO(user));
        }
        return userTableDTOS;
    }

    @Override
    public UserGetDTO getUserGetDTO(long id) {
        return new UserGetDTO(getUser(id));
    }

    @Override
    @Transactional
    public void saveUser(UserPostDTO userPostDTO) {
        User user = new User();
        List<Role> roles = roleService.getRoles();

        roles.removeIf(role -> !userPostDTO.role().contains(role.getRoleName()));

        user.setFirstName(userPostDTO.firstName());
        user.setLastName(userPostDTO.lastName());
        user.setAge(userPostDTO.age());
        user.setEmail(userPostDTO.email());
        user.setPassword(userPostDTO.password());
        user.setRoles(new HashSet<>(roles));
        saveUser(user);
    }

    @Override
    @Transactional
    public void updateUser(UserPutDTO userPutDTO) {
        User user = getUser(userPutDTO.id());
        List<Role> roles = roleService.getRoles();

        roles.removeIf(role -> !userPutDTO.role().contains(role.getRoleName()));

        user.setFirstName(userPutDTO.firstName());
        user.setLastName(userPutDTO.lastName());
        user.setAge(userPutDTO.age());
        user.setEmail(userPutDTO.email());
        user.setRoles(new HashSet<>(roles));
        if (userPutDTO.password() != null && !userPutDTO.password().isEmpty()) {
            user.setPassword(userPutDTO.password());
            updateUser(user);
        } else {
            updateUserWithOldPassword(user);
        }

    }
}
