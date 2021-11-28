package com.example.lab1.controllers;

import com.example.lab1.dto.UserLoginDto;
import com.example.lab1.services.ServiceCode;
import com.example.lab1.services.ServiceResult;
import com.example.lab1.services.UserService;
import com.example.lab1.utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    @Autowired
    Jwt jwt;

    @Autowired
    UserService userService;

    @PostMapping(value = "/api/auth")
    public ResponseEntity auth(@RequestBody UserLoginDto info){
        ServiceResult result = userService.login(info);

        if (result.id == ServiceCode.BAD_REQUEST){
            return ResponseEntity.badRequest().build();
        }

        String token = jwt.generateToken(info.login);

        return ResponseEntity.ok(token);
    }
}
