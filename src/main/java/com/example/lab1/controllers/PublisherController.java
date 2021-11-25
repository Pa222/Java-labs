package com.example.lab1.controllers;

import com.example.lab1.Filters;
import com.example.lab1.dto.PublisherDeleteDto;
import com.example.lab1.dto.PublisherDto;
import com.example.lab1.model.Publisher;
import com.example.lab1.services.PublisherService;
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

    @GetMapping(value = {"editpublishers"})
    public ModelAndView EditPublishersPage(Model model, @ModelAttribute("filters") Filters filters){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPublishers");

        int pagesAmount = Math.round((float)publisherService.getPublishersCount() / 20);

        model.addAttribute("publishers", publisherService.getPublishersByPageNumber(filters.getPageNumber(), 20));
        model.addAttribute("pagesAmount", pagesAmount);
        return modelAndView;
    }

    @PostMapping(value = "/deletepublisher")
    public ModelAndView delete(Model model, @ModelAttribute("Publisher") PublisherDeleteDto publisher,
                               @ModelAttribute("filters") Filters filters){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPublishers");

        ServiceResult result = publisherService.deletePublisher(publisher);

        int pagesAmount = Math.round((float)publisherService.getPublishersCount() / 20);

        model.addAttribute("publishers", publisherService.getPublishersByPageNumber(filters.getPageNumber(), 20));
        model.addAttribute("pagesAmount", pagesAmount);
        model.addAttribute("message", result.message);
        return modelAndView;
    }

    @GetMapping(value = "/editpublisher")
    public ModelAndView editPublisherPage(Model model, @ModelAttribute("Publisher") Publisher publisher){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPublisher");
        model.addAttribute("Publisher", publisherService.getById(publisher.getId()));
        return modelAndView;
    }

    @PostMapping(value = "/editpublisher")
    public ModelAndView editPublisher(Model model, @ModelAttribute("Publisher") Publisher publisher,
                                      @ModelAttribute("filters") Filters filters){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPublishers");

        ServiceResult result = publisherService.editPublisher(publisher);

        int pagesAmount = Math.round((float)publisherService.getPublishersCount() / 20);

        model.addAttribute("errorMessage", result.message);
        model.addAttribute("publishers", publisherService.getPublishersByPageNumber(filters.getPageNumber(), 20));
        model.addAttribute("pagesAmount", pagesAmount);

        return modelAndView;
    }
}
