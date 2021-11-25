package com.example.lab1.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PublisherDto {
    @NotNull
    @NotBlank
    private String publisherName;
}
