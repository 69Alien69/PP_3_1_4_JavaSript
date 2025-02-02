package ru.kata.spring.boot_security.demo.dto;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserGetDTO(long id, String firstName, String lastName, byte age, String email, List<String> roles) {
    public UserGetDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getAge(), user.getEmail(),
                user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()));
    }
}
