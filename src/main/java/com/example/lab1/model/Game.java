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
@Entity ///////////////////////////////////////////
@Table(name="Games") ///////////////////////////////
public class Game {
    @NotBlank
    @NotNull
    private String title;

    @NotBlank
    @NotNull
    private String publisher;

    @NotBlank
    @NotNull
    private String gameDescription;

    @NotBlank
    @NotNull
    private String rating;

    private float price;

    @Id /////////////////////////////////
    @GeneratedValue(strategy = GenerationType.IDENTITY) /////////////////////////////
    private Long id;

    public Game(String title, String publisher){
        this.title = title;
        this.publisher = publisher;
    }
}

