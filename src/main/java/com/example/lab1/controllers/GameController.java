package com.example.lab1.controllers;

import com.example.lab1.dto.GameDeleteDto;
import com.example.lab1.dto.GameDto;
import com.example.lab1.dto.GameEditDto;
import com.example.lab1.forms.GameForm;
import com.example.lab1.model.Game;
import com.example.lab1.model.Publisher;
import com.example.lab1.repos.GamesRepository;
import com.example.lab1.repos.PublisherRepository;
import com.example.lab1.services.GameService;
import com.example.lab1.services.ServiceCode;
import com.example.lab1.services.ServiceResult;
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
    private GameService gameService;

    @Autowired
    private PublisherRepository publisherRepository;

    @Value("${error.message}")
    private String allFieldsAreRequiredMessage;

    //Добавление
    @GetMapping(value = {"/addgame"})
    public ModelAndView showAddGamePage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addgame");
        model.addAttribute("Publishers", publisherRepository.findAll());
        return modelAndView;
    }

    @PostMapping(value = {"/addgame"})
    public ModelAndView saveNewGame(Model model,
                                    @ModelAttribute("gameDto") @Valid GameDto game, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addgame");

        System.out.println(game.title + ' ' + game.publisher + ' ' + game.price + ' ' + game.rating + ' ' + game.gameDescription);

        if (result.hasErrors()){
            model.addAttribute("message", allFieldsAreRequiredMessage);
            model.addAttribute("Publishers", publisherRepository.findAll());
            return modelAndView;
        }

        ServiceResult serviceResult = gameService.addGame(game);

        if (serviceResult.id == ServiceCode.BAD_REQUEST){
            model.addAttribute("message", serviceResult.message);
            model.addAttribute("Publishers", publisherRepository.findAll());
            return modelAndView;
        }

        model.addAttribute("message", "Game successfully added");
        model.addAttribute("Publishers", publisherRepository.findAll());

        return modelAndView;
    }

    @GetMapping(value = {"editgames"})
    public ModelAndView delete(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editGames");
        model.addAttribute("games", gameService.getGames());
        return modelAndView;
    }

    //Удаление
    @PostMapping(value = {"/deletegame"})
    public ModelAndView deleteGame(Model model, @ModelAttribute("Game") GameDeleteDto game){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editGames");

        ServiceResult result = gameService.deleteGame(game);

        model.addAttribute("games", gameService.getGames());
        model.addAttribute("message", result.message);
        return modelAndView;
//        gamesRepository.deleteById(game.getId());
//
//        model.addAttribute("games", gameService.getGames());
//
//        return  "redirect:/allgames";
    }

    //Редактирование
    @GetMapping(value = {"/editgame"})
    public ModelAndView editGamePage(Model model, @ModelAttribute("Game") GameEditDto game){
        ModelAndView modelAndView = new ModelAndView("editgame");
        model.addAttribute("Game", gameService.getGameById(game.id));
        model.addAttribute("Publishers", publisherRepository.findAll());
        return modelAndView;
    }

    @PostMapping(value = {"/editgame"})
    public ModelAndView editGame(Model model,
                                 @ModelAttribute("Game") @Valid GameEditDto game, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()){
            modelAndView.setViewName("editgame");
            model.addAttribute("message", allFieldsAreRequiredMessage);
            model.addAttribute("Publishers", publisherRepository.findAll());

            return modelAndView;
        }

        ServiceResult serviceresult = gameService.editGame(game);

        if (serviceresult.id == ServiceCode.BAD_REQUEST){
            modelAndView.setViewName("editgame");
            model.addAttribute("message", serviceresult.message);
            model.addAttribute("Publishers", publisherRepository.findAll());

            return modelAndView;
        }

        modelAndView.setViewName("editgames");
        model.addAttribute("games", gameService.getGames());
        model.addAttribute("message", serviceresult.message);

        return modelAndView;
    }

}
