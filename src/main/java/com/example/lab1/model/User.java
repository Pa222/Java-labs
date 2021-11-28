package com.example.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Users")
public class User {

    @NotBlank
    @NotNull
    private String login;

    @NotBlank
    @NotNull
    private String password;

    @NotBlank
    @NotNull
    private String Name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }
}