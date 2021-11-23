package com.example.lab1.controllers;

import com.example.lab1.Filters;
import com.example.lab1.model.Game;
import com.example.lab1.repos.GamesRepository;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

@RestController
public class CatalogController {

    @Autowired
    GamesRepository gamesRepository;

    @GetMapping(value = "/catalog")
    @Operation(description = "Filters data got from database and sends a response to client",
            summary = "Returns view with games list got from db")
    public ModelAndView catalog(Model model, @ModelAttribute("filters") Filters filters){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("gameslist");

        ArrayList<Game> games = new ArrayList<>();
        ArrayList<Game> searchCollection = new ArrayList<>();

        if (Objects.equals(filters.getSearchBox(), "")){
            switch(filters.getSort()){
                case "priceAsc":{
                    searchCollection.addAll((Collection<? extends Game>) gamesRepository.findAllSortedByPriceAscending());
                    break;
                }
                case "priceDesc":{
                    searchCollection.addAll((Collection<? extends Game>) gamesRepository.findAllSortedByPriceDescending());
                    break;
                }
                case "TitleAsc":{
                    searchCollection.addAll((Collection<? extends Game>) gamesRepository.findAllSortedByTitleAscending());
                    break;
                }
                case "TitleDesc":{
                    searchCollection.addAll((Collection<? extends Game>) gamesRepository.findAllSortedByTitleDescending());
                    break;
                }
            }
        }
        else {
            switch(filters.getSort()){
                case "priceAsc":{
                    searchCollection.addAll((Collection<? extends Game>) gamesRepository.findAllByTitleContainsOrderByPriceAsc(filters.getSearchBox()));
                    break;
                }
                case "priceDesc":{
                    searchCollection.addAll((Collection<? extends Game>) gamesRepository.findAllByTitleContainsOrderByPriceDesc(filters.getSearchBox()));
                    break;
                }
                case "TitleAsc":{
                    searchCollection.addAll((Collection<? extends Game>) gamesRepository.findAllByTitleContainsOrderByTitleAsc(filters.getSearchBox()));
                    break;
                }
                case "TitleDesc":{
                    searchCollection.addAll((Collection<? extends Game>) gamesRepository.findAllByTitleContainsOrderByTitleDesc(filters.getSearchBox()));
                    break;
                }
            }
        }

        for (Game game : searchCollection){
            if (
                    (!filters.isRating18() && Objects.equals(game.getRating(), "18+")) ||
                    (game.getPrice() < filters.getPriceFrom() || game.getPrice() > filters.getPriceTo())
            ){
                continue;
            }
            games.add(game);
        }



        model.addAttribute("filters", filters);
        model.addAttribute("games", games);

        return modelAndView;
    }
}
