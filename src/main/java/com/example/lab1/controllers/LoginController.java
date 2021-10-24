package com.example.lab1.controllers;

import com.example.lab1.UserContext;
import com.example.lab1.forms.UserForm;
import com.example.lab1.model.User;
import com.example.lab1.repos.UsersRepository;
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

    @GetMapping("/login-page")
    private ModelAndView loginPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Log in");
        return modelAndView;
    }

    @GetMapping("/login")
    private ModelAndView login(Model model, @ModelAttribute("User") User user){
        ModelAndView modelAndView = new ModelAndView();

        User dbUser = usersRepository.findByLogin(user.getLogin());

        if (dbUser == null){
            modelAndView.setViewName("Log in");
            model.addAttribute("errorMessage", "Пользователь не найден");
            return modelAndView;
        } else if (!Objects.equals(dbUser.getPassword(), user.getPassword())){
            modelAndView.setViewName("Log in");
            model.addAttribute("errorMessage", "Пароль не верный");
            return modelAndView;
        }

        UserContext.getInstance().value = dbUser;

        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }

    @GetMapping("/registration")
    private ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Register");
        return modelAndView;
    }

    @PostMapping("/register")
    private ModelAndView register(Model model, @ModelAttribute("User") @Valid UserForm userForm, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()){
            model.addAttribute("errorMessage", "Все поля должны быть заполнены");
            modelAndView.setViewName("Register");
            return modelAndView;
        }
        else if (!Objects.equals(userForm.getPassword(), userForm.getRepeatPassword())){
            model.addAttribute("errorMessage", "Пароли не совпадают");
            modelAndView.setViewName("Register");
            return modelAndView;
        }

        usersRepository.addNewUser(userForm.getLogin(), userForm.getPassword());

        modelAndView.setViewName("redirect:");
        return modelAndView;
    }
}
