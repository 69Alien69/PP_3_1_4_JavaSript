package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;

@Controller
@RequestMapping("/")
public class UserController {

    private final RoleService roleService;

    public UserController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public String getUser(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("currentUser", user);
        model.addAttribute("rolesList", roleService.getRoles());
        return "user";
    }
}
