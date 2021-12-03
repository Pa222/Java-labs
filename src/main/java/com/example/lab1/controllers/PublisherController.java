package com.example.lab1.controllers;

import com.example.lab1.dto.PublisherDeleteDto;
import com.example.lab1.dto.PublisherDto;
import com.example.lab1.model.Publisher;
import com.example.lab1.services.PublisherService;
import com.example.lab1.services.ServiceCode;
import com.example.lab1.services.ServiceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

@Controller
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @ApiResponse(code = 200,response = ResponseEntity.class, message = "OK")
    @Operation(description = "Creates new entry of publisher in the database using provided PublisherDto")
    @PostMapping(value = "/api/addpublisher")
    public ResponseEntity AddPublisher(@RequestBody PublisherDto publisher){
        ServiceResult serviceResult = publisherService.addPublisher(publisher);

        if (serviceResult.id == ServiceCode.BAD_REQUEST){
            return  ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @ApiResponse(code = 200,response = ResponseEntity.class, message = "OK")
    @Operation(description = "Removes entry of publiher from the database using provided PublisherDeleteDto")
    @DeleteMapping(value = "/api/deletepublisher")
    public ResponseEntity delete(@RequestBody PublisherDeleteDto publisher){
        ServiceResult result = publisherService.deletePublisher(publisher);

        if (result.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        ArrayList<Publisher> res = (ArrayList<Publisher>) publisherService.getPublishers();

        return ResponseEntity.ok(res);
    }

    @ApiResponse(code = 200,response = ResponseEntity.class, message = "OK")
    @Operation(description = "Updates entry of publisher in the database")
    @PutMapping(value = "/api/editpublisher")
    public ResponseEntity editPublisher(@RequestBody Publisher publisher){
        ServiceResult result = publisherService.editPublisher(publisher);

        if (result.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @ApiResponse(code = 200,response = ResponseEntity.class, message = "OK")
    @Operation(description = "Returns a list of publishers")
    @GetMapping(value = "api/get-publishers")
    public ResponseEntity getPublishers() throws IOException {
        Iterable<Publisher> publishers = publisherService.getPublishers();

        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, publishers);

        return ResponseEntity.ok(writer.toString());
    }

    @ApiResponse(code = 200,response = ResponseEntity.class, message = "OK")
    @Operation(description = "Returns a publisher using provided id")
    @GetMapping(value = "/api/get-publisher-by-id")
    public ResponseEntity getPublisherById(Long id){
        Publisher publisher = publisherService.getById(id);

        if (publisher == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(publisher);
    }
}
