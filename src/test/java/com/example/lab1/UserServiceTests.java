package com.example.lab1;

import com.example.lab1.model.Game;
import com.example.lab1.repos.GamesRepository;
import com.example.lab1.repos.UsersRepository;
import com.example.lab1.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    UsersRepository usersRepository;

    @Mock
    GamesRepository gamesRepository;

    @InjectMocks
    UserService userService;

    @Test
    void getUserOrderGames_ShouldReturnArrayList_Positive(){
        ArrayList<Integer> expectedIds = new ArrayList<>(4);
        Game expectedGame = new Game();
        when(usersRepository.getUserOrderGamesIds(1L, 1L)).thenReturn(expectedIds);
        when(gamesRepository.getGameById(1L)).thenReturn(expectedGame);
    }
}
