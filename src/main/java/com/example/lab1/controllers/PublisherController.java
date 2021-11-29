package com.example.lab1.controllers;

import com.example.lab1.dto.PublisherDeleteDto;
import com.example.lab1.dto.PublisherDto;
import com.example.lab1.model.Publisher;
import com.example.lab1.services.PublisherService;
import com.example.lab1.services.ServiceCode;
import com.example.lab1.services.ServiceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;

@Controller
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping(value = "/api/addpublisher")
    public ResponseEntity AddPublisher(@RequestBody PublisherDto publisher){
        ServiceResult serviceResult = publisherService.addPublisher(publisher);

        if (serviceResult.id == ServiceCode.BAD_REQUEST){
            return  ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/api/deletepublisher")
    public ResponseEntity delete(@RequestBody PublisherDeleteDto publisher){
        ServiceResult result = publisherService.deletePublisher(publisher);

        if (result.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/api/editpublisher")
    public ResponseEntity editPublisher(@RequestBody Publisher publisher){
        ServiceResult result = publisherService.editPublisher(publisher);

        if (result.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "api/get-publishers")
    public ResponseEntity getPublishers() throws IOException {
        Iterable<Publisher> publishers = publisherService.getPublishers();

        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, publishers);

        return ResponseEntity.ok(writer.toString());
    }
}
