package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public String getUsers(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("currentUser", user);
        return "apanel";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("rolesList", roleService.getRoles());
        return "add";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("rolesPicked") List<String> roleAuthorities) {

        Set<Role> roles = roleAuthorities.stream()
                .map(roleService::findByAuthority)
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:list";
    }

    @PostMapping("/delete")
    public String removeUser(@RequestParam("userId") Long id) {
        userService.removeUserById(id);
        return "redirect:list";
    }

    @GetMapping("/edit")
    public String editUserForm(Model model, @RequestParam("userId") Long id) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("rolesList", roleService.getRoles());
        return "edit";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") User user, @RequestParam("rolesPicked") List<String> roleAuthorities) {

        Set<Role> roles = roleAuthorities.stream()
                .map(roleService::findByAuthority)
                .collect(Collectors.toSet());

        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:list";
    }

}
