package com.example.lab1.controllers;

import com.example.lab1.aop.LogAnnotation;
import com.example.lab1.dto.CreateOrderDto;
import com.example.lab1.email.EmailService;
import com.example.lab1.model.Game;
import com.example.lab1.model.User;
import com.example.lab1.repos.UsersRepository;
import com.example.lab1.services.ServiceCode;
import com.example.lab1.services.ServiceResult;
import com.example.lab1.services.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BasketController {

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

    @Autowired
    UsersRepository usersRepository;

    @LogAnnotation
    @ApiResponse(code = 200, response = ResponseEntity.class, message = "Order created successfully")
    @Operation(description = "Creates new entry of order in the database and send order confirmation message to user email")
    @PostMapping(value = "/api/create-order")
    public ResponseEntity createOrder(@RequestBody CreateOrderDto order){
        if (order == null){
            return ResponseEntity.badRequest().build();
        }

        User user = userService.getUserByLogin(order.getUserEmail());

        if (user == null){
            return ResponseEntity.badRequest().build();
        }

        float total = 0;

        for (int i = 0; i < order.getGames().length; i++) {
            total += order.getGames()[i].getPrice();
        }

        ServiceResult result1 = userService.createOrder(user.getId(), total);

        if (result1.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        int orderId = userService.getLastOrderId(user.getId());

        ServiceResult result2 = userService.addGamesToOrder(orderId, order.getGames());

        if (result2.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        try{
            emailService.send(order.getUserEmail(), buildMessage(order.getGames(), user.getName(), total));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    private String buildMessage(Game[] games, String userName, float total){
        String out = "Thanks for your order!<br><br>" +
                "Dear, " + userName + ", here's the list of games you purchased:<br><br>" +
                "============================================<br>";
        for (int i = 0; i < games.length; i++) {
            Game game = games[i];
            out += i + 1 + ": " + game.getTitle() + " (" + game.getRating() + ") -> " + game.getPrice() + "$<br>";
        }

        out += "<br>Your total: " + String.format("%.2f", total) + "$<br>";
        out += "============================================<br><br>" +
                "Thanks for you money hehehehehe";
        return out;
    }
}
