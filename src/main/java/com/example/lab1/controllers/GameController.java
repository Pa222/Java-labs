package com.example.lab1.controllers;

import com.example.lab1.forms.GameForm;
import com.example.lab1.model.Game;
import com.example.lab1.repos.GamesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Slf4j
@Controller
public class GameController {
    @Autowired
    private GamesRepository gamesRepository;

    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String allFieldsAreRequiredMessage;

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
                                    @ModelAttribute("gameform") @Valid GameForm gameForm, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/allgames");
        if (result.hasErrors()){
            modelAndView.setViewName("addgame");
            model.addAttribute("errorMessage", allFieldsAreRequiredMessage);
            return modelAndView;
        }
        gamesRepository.addNewGame(gameForm.getPublisher(), gameForm.getTitle());
        model.addAttribute("games", gamesRepository.findAll());
        log.info("Game was added");
        return modelAndView;
    }

    //Удаление
    @PostMapping(value = {"/deletegame"})
    public String deleteGame(Model model, @ModelAttribute("gameToRemove") Game game){
        gamesRepository.deleteById(game.getId());

        log.info("Game was removed");
        model.addAttribute("games", gamesRepository.findAll());

        return  "redirect:/allgames";
    }

    //Редактирование
    @GetMapping(value = {"/editgame"})
    public ModelAndView editGamePage(Model model, @ModelAttribute("gameToEdit") Game game){
        ModelAndView modelAndView = new ModelAndView("editgame");
        Game toChange = gamesRepository.findById(game.getId()).get();
        GameForm gameform = new GameForm(toChange.getTitle(), toChange.getPublisher(), toChange.getId());
        model.addAttribute("gameForm", gameform);
        log.info("/editgame was called");
        return modelAndView;
    }

    @PostMapping(value = {"/editgame"})
    public ModelAndView editGame(Model model,
                                 @ModelAttribute("gameForm") @Valid Game game, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/allgames");

        if (result.hasErrors()){
            modelAndView.setViewName("editgame");
            model.addAttribute("errorMessage", allFieldsAreRequiredMessage);
            return modelAndView;
        }

        gamesRepository.updateGame(game.getPublisher(), game.getTitle(), game.getId());
        model.addAttribute("games", gamesRepository.findAll());

        log.info("Game was edited");
        return modelAndView;
    }

}
