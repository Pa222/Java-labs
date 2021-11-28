package com.example.lab1.controllers;

import com.example.lab1.Filters;
import com.example.lab1.model.Game;
import com.example.lab1.services.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Objects;

@RestController
public class CatalogController {

    @Autowired
    GameService gameService;

    @GetMapping(value = "/api/GetMoviesByPage")
    @Operation(description = "Filters data got from database and sends a response to client",
            summary = "Returns view with games list got from db")
    public ResponseEntity<?> catalog(int page, int size, String title) throws IOException {
        ArrayList<Game> games = gameService.getGamesByPageNumber(page, size, title);

        if (games == null){
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }

        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, games);

        return ResponseEntity.ok(writer.toString());
    }
}
