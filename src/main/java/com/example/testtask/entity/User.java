package com.example.testtask.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@Schema(description = "User entity")
public class User {
    @Id
    @Schema(description = "User ID", example = "1")
    private Long id;
    @Schema(description = "User username", example = "red_bull")
    private String username;
    @Schema(description = "User first name", example = "Bull")
    private String name;
    @Schema(description = "User last name", example = "Red")
    private String surname;

    public User() {
    }

    public User(Long id, String username, String name, String surname) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
