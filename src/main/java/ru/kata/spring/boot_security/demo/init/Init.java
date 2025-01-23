package ru.kata.spring.boot_security.demo.init;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Component
public class Init {
    private final UserService userService;

    public Init(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Role adminRole = new Role();
        adminRole.setAuthority("ROLE_ADMIN");

        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");

        User admin = new User();
        admin.setEmail("admin@mail");
        admin.setPassword("admin");
        admin.setRoles(Set.of(adminRole));
        admin.setFirstName("adminFirstName");
        admin.setLastName("adminLastName");
        admin.setAge((byte) 30);

        User user = new User();
        user.setEmail("user@mail");
        user.setPassword("user");
        user.setRoles(Set.of(userRole));
        user.setFirstName("userFirstName");
        user.setLastName("userLastName");
        user.setAge((byte) 35);

        userService.saveUser(admin);
        userService.saveUser(user);
    }
}
