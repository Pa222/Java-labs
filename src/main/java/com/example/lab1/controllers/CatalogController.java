package com.example.lab1.controllers;

import com.example.lab1.Filters;
import com.example.lab1.model.Game;
import com.example.lab1.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Objects;

@RestController
public class CatalogController {

    @Autowired
    GameService gameService;

    @GetMapping(value = {"/index", "/", "/catalog"})
    @Operation(description = "Filters data got from database and sends a response to client",
            summary = "Returns view with games list got from db")
    public ModelAndView catalog(Model model, @ModelAttribute("filters") Filters filters){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("gameslist");

        ArrayList<Game> result = new ArrayList<>();
        ArrayList<Game> games;
        int pagesAmount = 1;

        if (filters.getSearchBox().isEmpty()) {
            pagesAmount = Math.round((float) gameService.getGamesCount() / 8);
            games = gameService.getGamesByPageNumber(filters.getPageNumber(), 8, null);
        } else{
            pagesAmount = Math.round((float) gameService.getGamesByTitleCount(filters.getSearchBox()) / 8);
            games = gameService.getGamesByPageNumber(filters.getPageNumber(), 8, filters.getSearchBox());
        }

        switch (filters.getSort()) {
            case "priceAsc": {
                games.sort(Game.PRICE_ASCENDING_COMPARATOR);
                break;
            }
            case "priceDesc": {
                games.sort(Game.PRICE_DESCENDING_COMPARATOR);
                break;
            }
            case "TitleAsc": {
                games.sort(Game.TITLE_ASCENDING_COMPARATOR);
                break;
            }
            case "TitleDesc": {
                games.sort(Game.TITLE_DESCENDING_COMPARATOR);
                break;
            }
        }

        for (Game game : games) {
            if (
                    (!filters.isRating18() && Objects.equals(game.getRating(), "18+")) ||
                            (game.getPrice() < filters.getPriceFrom() || game.getPrice() > filters.getPriceTo())
            ) {
                continue;
            }
            result.add(game);
        }

        model.addAttribute("filters", filters);
        model.addAttribute("games", result);
        model.addAttribute("pagesAmount", pagesAmount);

        return modelAndView;
    }
}
