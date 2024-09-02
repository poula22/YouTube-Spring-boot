package com.example.usermicroservice.domain.entity;

import com.example.domain.customValidation.email.CustomEmailValidation;
import com.example.domain.customValidation.password.CustomPasswordValidation;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "password")
    @CustomPasswordValidation
    private String password;

    @Basic
    @Column(name = "email")
    @CustomEmailValidation
    private String email;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(password, that.password) && Objects.equals(email, that.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email);
    }
}