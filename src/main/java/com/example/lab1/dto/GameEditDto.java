package com.example.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class GameEditDto {

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

    public Long id;

    public float price;
}
