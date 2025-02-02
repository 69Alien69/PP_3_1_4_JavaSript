package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDeleteDTO;
import ru.kata.spring.boot_security.demo.dto.UserGetDTO;
import ru.kata.spring.boot_security.demo.dto.UserPostDTO;
import ru.kata.spring.boot_security.demo.dto.UserPutDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users_table")
    public List<UserGetDTO> getAllUsers() {
        return userService.getUsersGetDTO();
    }

    @GetMapping("/get_roles")
    public List<Role> getRoles() {
        return roleService.getRoles();
    }

    @GetMapping("/user")
    public UserGetDTO getCurrentUser(@AuthenticationPrincipal User user) {
        return userService.getUserGetDTO(user.getId());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserPostDTO userPostDTO) {
        userService.saveUser(userPostDTO);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editUser(@RequestBody UserPutDTO userPutDTO) {
        userService.updateUser(userPutDTO);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteDTO userDeleteDTO) {
        userService.removeUserById(userDeleteDTO.id());
        return ResponseEntity.ok("ok");
    }
}
