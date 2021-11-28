package com.example.lab1.services;

import com.example.lab1.dto.UserLoginDto;
import com.example.lab1.dto.UserRegisterDto;
import com.example.lab1.model.User;
import com.example.lab1.repos.UsersRepository;
import com.example.lab1.utils.Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    public ServiceResult login(UserLoginDto info){
        User user = usersRepository.getByLogin(info.login);

        if (user == null){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "User doesn't exists");
        }

        if (!Hasher.isValid(info.password, Hasher.fromHex(user.getPassword()), user.getSalt())){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Password incorrect");
        }

        return new ServiceResult(ServiceCode.OK, "User authorized");
    }

    public ServiceResult register(UserRegisterDto info){
        User user = new User();
        try{
            byte[] salt = Hasher.getSalt();
            byte[] hashedPassword = Hasher.getSaltedHash(info.password, salt);
            user.setLogin(info.login);
            user.setSalt(salt);
            user.setPassword(Hasher.toHex(hashedPassword));
            user.setName(info.name);
            usersRepository.save(user);
            return new ServiceResult(ServiceCode.CREATED, "User registered");
        } catch (NoSuchAlgorithmException e) {
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Server error: " + e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return usersRepository.getByLogin(s);
    }
}
