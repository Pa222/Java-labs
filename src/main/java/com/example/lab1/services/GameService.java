package com.example.lab1.services;

import com.example.lab1.dto.GameDeleteDto;
import com.example.lab1.dto.GameDto;
import com.example.lab1.dto.GameEditDto;
import com.example.lab1.model.Game;
import com.example.lab1.repos.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GameService {
    @Autowired
    GamesRepository gamesRepository;

    private final Pattern _pattern = Pattern.compile("\\d{2}\\+");

    public Game getGameById(Long id) { return gamesRepository.findById(id).get(); }

    public ServiceResult addGame(GameDto info){

        Matcher matcher = _pattern.matcher(info.rating);

        if (!matcher.matches()){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Rating incorrect. Example: 18+");
        }

        gamesRepository.addNewGame(info.publisher, info.title, info.rating, info.price, info.gameDescription);

        return new ServiceResult(ServiceCode.CREATED, "Game added");
    }

    public ServiceResult editGame(GameEditDto info){

        Matcher matcher = _pattern.matcher(info.rating);

        if (!matcher.matches()){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Rating incorrect. Example: 18+");
        }

        gamesRepository.updateGame(info.id, info.publisher, info.title, info.rating, info.price, info.gameDescription);

        return new ServiceResult(ServiceCode.CREATED, "Game successfully edited");
    }

    public ServiceResult deleteGame(GameDeleteDto info){
        gamesRepository.deleteById(info.id);

        return new ServiceResult(ServiceCode.OK, "Game successfully deleted");
    }

    public Iterable<Game> getGames(){
        return gamesRepository.findAll();
    }
}
