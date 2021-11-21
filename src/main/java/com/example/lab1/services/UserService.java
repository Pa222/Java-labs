package com.example.lab1.services;

import com.example.lab1.dto.UserLoginDto;
import com.example.lab1.dto.UserRegisterDto;
import com.example.lab1.model.User;
import com.example.lab1.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;

@Service
public class UserService{

    @Autowired
    UsersRepository usersRepository;

    public ServiceResult login(UserLoginDto info){
        User user = usersRepository.findByLogin(info.login);

        if (user == null){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "User doesn't exists");
        }

        if (!Objects.equals(user.getPassword(), info.password)){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Password incorrect");
        }

        return new ServiceResult(ServiceCode.OK, "User authorized");
    }

    public ServiceResult register(UserRegisterDto info){
        if (!Objects.equals(info.password, info.repeatPassword)){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Passwords must match");
        }
        usersRepository.addNewUser(info.login, info.password);
        return new ServiceResult(ServiceCode.CREATED, "User registered");
    }
}
