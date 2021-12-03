package com.example.lab1.controllers;

import com.example.lab1.model.Game;
import com.example.lab1.services.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

@RestController
public class CatalogController {

    @Autowired
    GameService gameService;

    @ApiResponse(code = 200,response = ResponseEntity.class, message = "OK")
    @Operation(description = "Returns a list of games by requested page number and page size")
    @GetMapping(value = "/api/GetGamesByPage")
    public ResponseEntity<?> catalog(int page, int size, String title, String sort, int priceFrom, int priceTo, boolean rating18) throws IOException {
        ArrayList<Game> games = new ArrayList<>();

        if (title == ""){
            games = gameService.getGamesByPageNumber(page, size, null);
        }
        else {
            games = gameService.getGamesByPageNumber(page, size, title);
        }

        if (games == null){
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }

        switch (sort){
            case "priceAsc":
                games.sort(Game.PRICE_ASCENDING_COMPARATOR);
                break;
            case "priceDesc":
                games.sort(Game.PRICE_DESCENDING_COMPARATOR);
                break;
            case "titleAsc":
                games.sort(Game.TITLE_ASCENDING_COMPARATOR);
                break;
            case "titleDesc":
                games.sort(Game.TITLE_DESCENDING_COMPARATOR);
                break;
        }

        ArrayList<Game> out_games = new ArrayList<>();
        
        for(Game game : games){
            if (rating18){
                if (game.getPrice() >= priceFrom && game.getPrice() <= priceTo){
                    out_games.add(game);
                }
            }
            else if (!game.getRating().equals("18+")){
                if (game.getPrice() >= priceFrom && game.getPrice() <= priceTo){
                    out_games.add(game);
                }
            }
        }

        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, out_games);

        return ResponseEntity.ok(writer.toString());
    }
}
