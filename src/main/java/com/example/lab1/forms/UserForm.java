package com.example.lab1.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    @NotBlank
    @NotNull
    private String login;

    @NotBlank
    @NotNull
    private String password;

    @NotBlank
    @NotNull
    private String repeatPassword;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public UserForm(String login, String password){
        this.login = login;
        this.password = password;
    }
}