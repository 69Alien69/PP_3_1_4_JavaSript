//package ru.kata.spring.boot_security.demo.model;
//
//import org.springframework.security.core.GrantedAuthority;
//
//import jakarta.persistence.*;
//import java.util.Objects;
//import java.util.Set;
//
//@Entity
//public class Role implements GrantedAuthority {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;
//
//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }
//
//    @Override
//    public String toString() {
//        return "Role{" +
//                "id=" + id +
//                ", users=" + users +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Role role = (Role) o;
//        return Objects.equals(id, role.id) && Objects.equals(users, role.users);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, users);
//    }
//
//    @Override
//    public String getAuthority() {
//        return "";
//    }
//}
