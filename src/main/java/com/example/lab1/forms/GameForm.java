package com.example.lab1.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameForm {
    private String title;
    private String publisher;
    private int id;
}
