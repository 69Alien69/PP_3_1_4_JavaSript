package ru.kata.spring.boot_security.demo.init;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Component
public class Init {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public Init(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        Role adminRole = new Role();
        adminRole.setAuthority("ROLE_ADMIN");

        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");

        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@mail");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(Set.of(adminRole));
        admin.setName("adminName");
        admin.setLastName("adminLastName");

        User user = new User();
        user.setUsername("user");
        user.setEmail("user@mail");
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(Set.of(userRole));
        user.setName("userName");
        user.setLastName("userLastName");

        userService.saveUser(admin);
        userService.saveUser(user);
    }
}
