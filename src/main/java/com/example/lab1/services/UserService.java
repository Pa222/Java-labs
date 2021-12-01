package com.example.lab1.services;

import com.example.lab1.dto.GameOrderInfoDto;
import com.example.lab1.dto.UserLoginDto;
import com.example.lab1.dto.UserRegisterDto;
import com.example.lab1.model.Game;
import com.example.lab1.model.User;
import com.example.lab1.repos.GamesRepository;
import com.example.lab1.repos.UsersRepository;
import com.example.lab1.utils.Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    GamesRepository gamesRepository;

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
        User check = usersRepository.getByLogin(info.login);

        if (check != null){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Email already taken");
        }

        User user = new User();
        try{
            byte[] salt = Hasher.getSalt();
            byte[] hashedPassword = Hasher.getSaltedHash(info.password, salt);
            usersRepository.createUser(info.login, Hasher.toHex(hashedPassword), salt, info.name);
            return new ServiceResult(ServiceCode.CREATED, "User registered");
        } catch (NoSuchAlgorithmException e) {
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Server error: " + e.getMessage());
        }
    }

    public ArrayList<GameOrderInfoDto> getUserOrderGames (Long orderId, Long userId){
        ArrayList<Integer> ids = (ArrayList<Integer>) usersRepository.getUserOrderGamesIds(orderId, userId);

        ArrayList<GameOrderInfoDto> res = new ArrayList<>();

        for (int id : ids){
            Game game = gamesRepository.getGameById((long) id);
            res.add(new GameOrderInfoDto(game.getTitle(), game.getPublisher().getPublisherName(),
                    game.getRating(), game.getPrice()));
        }

        return res;
    }

    public ServiceResult createOrder(Long userId, float total){
        try{
            usersRepository.createOrder(userId, total);
        } catch (Exception e){
            return new ServiceResult(ServiceCode.BAD_REQUEST, e.getMessage());
        }
        return new ServiceResult(ServiceCode.CREATED, "Success");
    }

    @Transactional
    public ServiceResult addGamesToOrder(int orderId, Game[] games){
        try{
            for (int i = 0; i < games.length; i++) {
                usersRepository.addGameToOrder((long) orderId, games[i].getId());
            }
        } catch(Exception e){
            return new ServiceResult(ServiceCode.BAD_REQUEST, e.getMessage());
        }
        return new ServiceResult(ServiceCode.OK, "Success");
    }

    public User getUserByLogin(String login){return usersRepository.getByLogin(login);}

    public Integer getLastOrderId(Long userId){return usersRepository.getLastOrderId(userId);}

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return usersRepository.getByLogin(s);
    }
}
