package com.example.lab1.controllers;

import com.example.lab1.UserContext;
import com.example.lab1.dto.UserLoginDto;
import com.example.lab1.dto.UserRegisterDto;
import com.example.lab1.model.User;
import com.example.lab1.repos.UsersRepository;
import com.example.lab1.services.ServiceCode;
import com.example.lab1.services.ServiceResult;
import com.example.lab1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserService userService;

//    @GetMapping("/login")
//    private ModelAndView loginPage(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("Log in");
//        return modelAndView;
//    }
//
//    @PostMapping("/login")
//    private ModelAndView login(Model model, @ModelAttribute("User") UserLoginDto user){
//        ModelAndView modelAndView = new ModelAndView();
//
//        ServiceResult serviceResult = userService.login(user);
//
//        if (serviceResult.id == ServiceCode.BAD_REQUEST){
//            model.addAttribute("errorMessage", serviceResult.message);
//            modelAndView.setViewName("Log In");
//            return modelAndView;
//        }
//
//        modelAndView.setViewName("redirect:/index");
//        return modelAndView;
//    }

    @GetMapping("/registration")
    private ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Register");
        return modelAndView;
    }

    @PostMapping("/registration")
    private ModelAndView register(Model model, @ModelAttribute("User") @Valid UserRegisterDto user, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()){
            model.addAttribute("errorMessage", "Все поля должны быть заполнены");
            modelAndView.setViewName("Register");
            return modelAndView;
        }

        ServiceResult serviceResult = userService.register(user);

        if (serviceResult.id == ServiceCode.BAD_REQUEST){
            model.addAttribute("errorMessage", serviceResult.message);
            modelAndView.setViewName("Register");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }
}
