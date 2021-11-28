package com.example.lab1.services;

import com.example.lab1.dto.UserLoginDto;
import com.example.lab1.dto.UserRegisterDto;
import com.example.lab1.model.User;
import com.example.lab1.repos.UsersRepository;
import com.example.lab1.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService{

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ServiceResult login(UserLoginDto info){
        User user = usersRepository.getByLogin(info.login);

        if (user == null){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "User doesn't exists");
        }

        if (!user.getPassword().equals(info.password)){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Password incorrect");
        }

        return new ServiceResult(ServiceCode.OK, "User authorized");
    }

    public ServiceResult register(UserRegisterDto info){
        if (!Objects.equals(info.password, info.repeatPassword)){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Passwords must match");
        }
        User user = new User();
        user.setLogin(info.login);
        user.setPassword(info.password);
        user.setName(info.name);
        usersRepository.save(user);
        return new ServiceResult(ServiceCode.CREATED, "User registered");
    }

}
