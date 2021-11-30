package com.example.lab1.controllers;

import com.example.lab1.dto.GameOrderInfoDto;
import com.example.lab1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class BasketController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/api/get-user-orders")
    public ResponseEntity getUserOrders(Long orderId, Long userId){
        ArrayList<GameOrderInfoDto> games = userService.getUserOrderGames(orderId, userId);

        if (games == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(games);
    }
}
