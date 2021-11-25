package com.example.lab1.controllers;

import com.example.lab1.dto.PublisherDto;
import com.example.lab1.services.PublisherService;
import com.example.lab1.services.ServiceCode;
import com.example.lab1.services.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping(value = "/addpublisher")
    public ModelAndView AddPublisherPage(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addPublisher");
        return modelAndView;
    }

    @PostMapping(value = "/addpublisher")
    public ModelAndView AddPublisher(Model model,
                                     @ModelAttribute("publisherDto") PublisherDto publisher, BindingResult result){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addPublisher");

        if (result.hasErrors()){
            model.addAttribute("message", "Все поля обязательны");
            return modelAndView;
        }

        ServiceResult serviceResult = publisherService.addPublisher(publisher);

        model.addAttribute("message", serviceResult.message);
        return modelAndView;
    }
}
