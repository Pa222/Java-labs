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
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PublisherServiceTests {
    @Mock
    PublisherRepository publisherRepository;

    @InjectMocks
    PublisherService publisherService;

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
