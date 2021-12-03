package com.example.lab1;

import com.example.lab1.model.Game;
import com.example.lab1.model.Publisher;
import com.example.lab1.repos.GamesRepository;
import com.example.lab1.repos.PublisherRepository;
import com.example.lab1.services.GameService;
import com.example.lab1.services.PublisherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class GameServiceTests {
    @Mock
    GamesRepository gamesRepository;

    @Mock
    PublisherRepository publisherRepository;

    @InjectMocks
    GameService gamesService;

    @InjectMocks
    PublisherService publisherService;

    @Test
    void getGameById_ShouldReturnGame_Positive(){
        long id = 1337L;
        Game expected = new Game("q", new Publisher(), "asd", "18+", 10, id);
        when(gamesRepository.getGameById(id)).thenReturn(expected);
        var actual = gamesService.getGameById(id);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getGamesByPageNumber_ShouldReturnArrayList_Positive(){
        int page = 1;
        int size = 8;

        ArrayList<Game> expected = new ArrayList<Game>(8);
        when(gamesRepository.getGamesByPageNumber(page, size, null)).thenReturn(expected);
        var actual = gamesService.getGamesByPageNumber(page, size, null);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getById_ShouldReturnPublisher_Positive(){
        long id = 2;
        Publisher expected = new Publisher(id, "Test");
        when(publisherRepository.getById(id)).thenReturn(expected);
        var actual = publisherService.getById(id);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void getPublishers_ShouldReturnIterable_Positive(){
        Iterable<Publisher> expected = new ArrayList<Publisher>(4);
        when(publisherRepository.getPublishers()).thenReturn(expected);
        var actual = publisherService.getPublishers();
        assertThat(actual).isEqualTo(expected);
    }
}
