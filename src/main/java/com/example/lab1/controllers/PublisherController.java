package com.example.lab1.controllers;

import com.example.lab1.dto.PublisherDeleteDto;
import com.example.lab1.dto.PublisherDto;
import com.example.lab1.model.Publisher;
import com.example.lab1.services.PublisherService;
import com.example.lab1.services.ServiceCode;
import com.example.lab1.services.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping(value = "/addpublisher")
    public ResponseEntity AddPublisher(PublisherDto publisher){
        ServiceResult serviceResult = publisherService.addPublisher(publisher);

        if (serviceResult.id == ServiceCode.BAD_REQUEST){
            return  ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/deletepublisher")
    public ResponseEntity delete(PublisherDeleteDto publisher){
        ServiceResult result = publisherService.deletePublisher(publisher);

        if (result.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/editpublisher")
    public ResponseEntity editPublisher(Publisher publisher){
        ServiceResult result = publisherService.editPublisher(publisher);

        if (result.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
