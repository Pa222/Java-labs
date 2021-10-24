package com.example.lab1.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class GameForm {
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

    private Long id;

    public GameForm(String title, String publisher, Long id){
        this.title = title;
        this.publisher = publisher;
        this.id = id;
    }
}
