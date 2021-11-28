package com.example.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GameDto {

    @NotBlank
    @NotNull
    public String title;
    @NotBlank
    @NotNull
    public String publisher;
    @NotBlank
    @NotNull
    public String rating;
    @NotBlank
    @NotNull
    public String gameDescription;

    public float price;

    public GameDto(String title, String publisher, String rating, String gameDescription){
        this.title = title;
        this.publisher = publisher;
        this.rating = rating;
        this.gameDescription = gameDescription;
    }
}
