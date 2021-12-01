package com.example.lab1.controllers;

import com.example.lab1.dto.CreateOrderDto;
import com.example.lab1.dto.GameOrderInfoDto;
import com.example.lab1.email.EmailService;
import com.example.lab1.model.Game;
import com.example.lab1.model.User;
import com.example.lab1.repos.UsersRepository;
import com.example.lab1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class BasketController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping(value = "/api/get-user-orders")
    public ResponseEntity getUserOrders(Long orderId, Long userId){
        ArrayList<GameOrderInfoDto> games = userService.getUserOrderGames(orderId, userId);

        if (games == null){
            return ResponseEntity.badRequest().build();
        }

        User user = usersRepository.findById(userId).get();

        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/api/create-order")
    public ResponseEntity createOrder(@RequestBody CreateOrderDto order){
        if (order == null){
            return ResponseEntity.badRequest().build();
        }

        User user = userService.getUserByLogin(order.getUserEmail());

        if (user == null){
            return ResponseEntity.badRequest().build();
        }

        try{
            emailService.send(order.getUserEmail(), buildMessage(order.getGames(), user.getName()));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    private String buildMessage(Game[] games, String userName){
        float total = 0;
        String out = "Thanks for your order!<br><br>" +
                "Dear, " + userName + ", here's the list of games you purchased:<br><br>" +
                "============================================<br>";
        for (int i = 0; i < games.length; i++) {
            Game game = games[i];
            out += i + 1 + ": " + game.getTitle() + " (" + game.getRating() + ") -> " + game.getPrice() + "$<br>";
            total += game.getPrice();
        }

        out += "<br>Your total: " + String.format("%.2f", total) + "$<br>";
        out += "============================================<br><br>" +
                "Thanks for you money hehehehehe";
        return out;
    }
}
