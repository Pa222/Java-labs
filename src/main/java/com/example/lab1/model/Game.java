package com.example.lab1.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.Collator;
import java.util.Comparator;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
@Entity
@Table(name="Games")
public class Game {

    public static final Comparator<Game> PRICE_ASCENDING_COMPARATOR = new Comparator<Game>() {
        @Override
        public int compare(Game o1, Game o2) {
            return (int) (o1.getPrice() - o2.getPrice());
        }
    };

    public static final Comparator<Game> PRICE_DESCENDING_COMPARATOR = new Comparator<Game>() {
        @Override
        public int compare(Game o1, Game o2) {
            return (int) (o2.getPrice() - o1.getPrice());
        }
    };

    public static final Comparator<Game> TITLE_ASCENDING_COMPARATOR = new Comparator<Game>() {
        @Override
        public int compare(Game o1, Game o2) {
            return Collator.getInstance().compare(o1.getTitle(), o2.getTitle());
        }
    };

    public static final Comparator<Game> TITLE_DESCENDING_COMPARATOR = new Comparator<Game>() {
        @Override
        public int compare(Game o1, Game o2) {
            return Collator.getInstance().compare(o2.getTitle(), o1.getTitle());
        }
    };

    @NotBlank
    @NotNull
    @Column(unique = true)
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Game(String title, Publisher publisher){
        this.title = title;
        this.publisher = publisher;
    }
}

