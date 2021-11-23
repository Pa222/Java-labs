package com.example.lab1.services;

import com.example.lab1.dto.GameDeleteDto;
import com.example.lab1.dto.GameDto;
import com.example.lab1.dto.GameEditDto;
import com.example.lab1.model.Game;
import com.example.lab1.model.Publisher;
import com.example.lab1.repos.GamesRepository;
import com.example.lab1.repos.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GameService {
    @Autowired
    GamesRepository gamesRepository;

    @Autowired
    PublisherRepository publisherRepository;

    private final Pattern _pattern = Pattern.compile("\\d{2}\\+|\\d\\+");

    public Game getGameById(Long id) { return gamesRepository.getGameById(id); }

    public ServiceResult addGame(GameDto info){

        Matcher matcher = _pattern.matcher(info.rating);

        if (!matcher.matches()){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Rating incorrect. Example: 18+");
        }

        Publisher publisher = publisherRepository.findByName(info.publisher);

        gamesRepository.addNewGame(publisher.getId(), info.title, info.rating, info.price, info.gameDescription);

        return new ServiceResult(ServiceCode.CREATED, "Game added");
    }

    public ServiceResult editGame(GameEditDto info){

        Matcher matcher = _pattern.matcher(info.rating);

        if (!matcher.matches()){
            return new ServiceResult(ServiceCode.BAD_REQUEST, "Rating incorrect. Example: 18+");
        }

        Publisher publisher = publisherRepository.findByName(info.publisher);

        gamesRepository.updateGame(info.id, publisher.getId(), info.title, info.rating, info.price, info.gameDescription);

        return new ServiceResult(ServiceCode.CREATED, "Game successfully edited");
    }

    public ServiceResult deleteGame(GameDeleteDto info){
        gamesRepository.deleteGame(info.id);

        return new ServiceResult(ServiceCode.OK, "Game successfully deleted");
    }

    public Iterable<Game> getGames(){
        return gamesRepository.getGames();
    }
}
