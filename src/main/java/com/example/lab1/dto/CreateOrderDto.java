package com.example.lab1.dto;

import com.example.lab1.model.Game;
import lombok.Data;

@Data
public class CreateOrderDto {
    private Game[] games;
    private String userEmail;
}
