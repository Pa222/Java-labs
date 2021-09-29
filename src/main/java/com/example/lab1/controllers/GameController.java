package com.example.lab1.controllers;

import com.example.lab1.forms.GameForm;
import com.example.lab1.model.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
public class GameController {
    private static List<Game> games = new ArrayList<Game>();
    static {
        games.add(new Game("Dead by Daylight", "Behavior"));
        games.add(new Game("Dota 2", "Valve"));
    }

    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;

    @GetMapping(value = {"/", "/index"})
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("/index was called");
        return modelAndView;
    }

    @GetMapping(value = {"/allgames"})
    public ModelAndView gamesList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("gameslist");
        model.addAttribute("games", games);
        log.info("/allgames was called");
        return modelAndView;
    }

    //Добавление
    @GetMapping(value = {"/addgame"})
    public ModelAndView showAddGamePage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addgame");
        GameForm gameform = new GameForm();
        model.addAttribute("gameform", gameform);
        log.info("/addgame was called");
        return modelAndView;
    }

    @PostMapping(value = {"/addgame"})
    public ModelAndView saveNewGame(Model model,
                                   @ModelAttribute("gameform") GameForm gameForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("gameslist");
        String title = gameForm.getTitle();
        String publisher = gameForm.getPublisher();
        if (title != null && title.length() > 0 //
                && publisher != null && publisher.length() > 0) {
            Game newGame = new Game(title, publisher);
            games.add(newGame);
            model.addAttribute("games",games);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addgame");
        log.info("Game was added");
        return modelAndView;
    }

    //Удаление
    @PostMapping(value = {"/deletegame"})
    public ModelAndView deleteGame(Model model, @ModelAttribute("gameToRemove") Game game){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("gameslist");

        String title = game.getTitle();
        String publisher = game.getPublisher();

        for (int i = 0; i < games.size(); i++)
            if (games.get(i).getTitle().equals(title) && games.get(i).getPublisher().equals(publisher)) {
                games.remove(i);
                break;
            }
        log.info("Game was removed");
        model.addAttribute("games", games);
        return  modelAndView;
    }

    //Редактирование
    @GetMapping(value = {"/editgame"})
    public ModelAndView editGamePage(Model model, @ModelAttribute("gameToEdit") Game game){
        ModelAndView modelAndView = new ModelAndView("editgame");
        GameForm gameform = new GameForm();
        gameform.setTitle(game.getTitle());
        gameform.setPublisher(game.getPublisher());
        gameform.setId(game.getId());
        model.addAttribute("gameform", gameform);
        log.info("/editgame was called");
        return modelAndView;
    }

    @PostMapping(value = {"/editgame"})
    public ModelAndView editGame(Model model, @ModelAttribute("gameToEdit") Game game){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("gameslist");
        String title = game.getTitle();
        String publisher = game.getPublisher();
        if (title != null && title.length() > 0 && publisher != null && publisher.length() > 0) {
            games.get(game.getId()).setTitle(game.getTitle());
            games.get(game.getId()).setPublisher(game.getPublisher());
            model.addAttribute("games",games);
            return modelAndView;
        }
        log.info("Game was edited");
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("editgame");
        return modelAndView;
    }

}
