package com.example.lab1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Games")
public class Game {
    @NotBlank
    @NotNull
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private Publisher publisher;

    @NotBlank
    @NotNull
    @Column(length = 4000)
    private String gameDescription;

    @NotBlank
    @NotNull
    private String rating;

    @NotBlank
    @NotNull
    private float price;

    @ManyToMany(mappedBy = "games")
    Set<Order> orders;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Game(String title, Publisher publisher){
        this.title = title;
        this.publisher = publisher;
    }
}

