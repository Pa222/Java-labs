package com.example.lab1.services;

import com.example.lab1.dto.UserLoginDto;
import com.example.lab1.dto.UserRegisterDto;
import com.example.lab1.model.User;
import com.example.lab1.repos.UsersRepository;
import com.example.lab1.utils.Hasher;
import com.example.lab1.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ServiceResult login(UserLoginDto info){
        User user = usersRepository.getByLogin(info.login);

        if (user == null){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "User doesn't exists");
        }

        if (!user.getPassword().equals(passwordEncoder.bCryptPasswordEncoder().encode(info.password))){
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
        user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(info.password));
        user.setName(info.name);
        usersRepository.save(user);
        return new ServiceResult(ServiceCode.CREATED, "User registered");
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return usersRepository.getByLogin(login);
    }
}
