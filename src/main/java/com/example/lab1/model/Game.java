package com.example.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private static int counter = 0;
    private String title;
    private String publisher;
    private int id;

    public Game(String title, String publisher){
        this.title = title;
        this.publisher = publisher;
        this.id = counter++;
    }
}

