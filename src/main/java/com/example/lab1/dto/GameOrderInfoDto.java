package com.example.lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class GameOrderInfoDto {

    @NotBlank
    @NotNull
    public String title;
    @NotBlank
    @NotNull
    public String publisher;
    @NotBlank
    @NotNull
    public String rating;

    public float price;
}

