package com.example.lab1.controllers;

import com.example.lab1.repos.GamesRepository;
import com.example.lab1.repos.OrderRepository;
import com.example.lab1.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BasketController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    GamesRepository gamesRepository;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping(value = "/basket")
    private ModelAndView GetBasket(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Basket");
        return modelAndView;
    }
}
