package com.example.lab1.dto;

import lombok.Data;

@Data
public class OrderInfoDto {
    private Long orderId;
    private GameOrderInfoDto[] games;
}
