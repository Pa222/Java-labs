package com.example.lab1.controllers;

import com.example.lab1.dto.GameDeleteDto;
import com.example.lab1.dto.GameDto;
import com.example.lab1.dto.GameEditDto;
import com.example.lab1.model.Game;
import com.example.lab1.services.GameService;
import com.example.lab1.services.ServiceCode;
import com.example.lab1.services.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Slf4j
@Controller
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping(value = {"/api/addgame"})
    public ResponseEntity saveNewGame(@RequestBody GameDto game) {
        ServiceResult serviceResult = gameService.addGame(game);

        if (serviceResult.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/api/get-pages-amount")
    public ResponseEntity getPagesAmount(){
        return ResponseEntity.ok(Math.round(gameService.getGamesCount() / 8));
    }

    @DeleteMapping(value = {"/api/deletegame"})
    public ResponseEntity deleteGame(@RequestBody GameDeleteDto game){
        ServiceResult result = gameService.deleteGame(game);

        if (result.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        ArrayList<Game> games = gameService.getGamesByPageNumber(1, 8, null);

        return ResponseEntity.ok(games);
    }

    @PutMapping(value = {"/api/editgame"})
    public ResponseEntity editGame(@RequestBody GameEditDto game){
        ServiceResult serviceresult = gameService.editGame(game);

        if (serviceresult.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/api/get-game-by-id")
    public ResponseEntity getGameById(Long id){
        Game game = gameService.getGameById(id);

        if (game == null){
            return ResponseEntity.badRequest().build();
        }

        GameEditDto result = new GameEditDto(game.getTitle(), game.getPublisher().getPublisherName(),
                game.getRating(), game.getGameDescription(), game.getId(), game.getPrice());

        return ResponseEntity.ok(result);
    }
}
