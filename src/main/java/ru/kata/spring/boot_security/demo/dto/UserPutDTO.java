package ru.kata.spring.boot_security.demo.dto;

public record UserPutDTO(long id, String firstName, String lastName, byte age, String email, String password, String role) {
}
