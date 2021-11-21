package com.example.lab1.repos;

import com.example.lab1.model.Game;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface GamesRepository extends CrudRepository<Game, Long>{
    @Modifying
    @Query(value = "insert into Games (publisher_id, title, rating, price, game_description) " +
            "values (:publisher, :title, :rating, :price, :gameDescription)", nativeQuery = true)
    @Transactional
    void addNewGame(
            @Param("publisher") Long publisher,
            @Param("title") String title,
            @Param("rating") String rating,
            @Param("price") float price,
            @Param("gameDescription") String gameDescription
    );

    @Modifying
    @Query(value = "update Games set publisher_id = :publisher, title = :title, rating = :rating, " +
            " price = :price, game_description = :gameDescription where id = :id", nativeQuery = true)
    @Transactional
    void updateGame(
                    @Param("id") Long id,
                    @Param("publisher") Long publisher,
                    @Param("title") String title,
                    @Param("rating") String rating,
                    @Param("price") float price,
                    @Param("gameDescription") String gameDescription
    );

    Iterable<Game> findByTitleContains(String Title);

    @Query(value = "select * from Games order by price asc", nativeQuery = true)
    Iterable<Game> findAllSortedByPriceAscending();

    @Query(value = "select * from Games order by price desc", nativeQuery = true)
    Iterable<Game> findAllSortedByPriceDescending();

    @Query(value = "select * from Games order by title asc", nativeQuery = true)
    Iterable<Game> findAllSortedByTitleAscending();

    @Query(value = "select * from Games order by title desc", nativeQuery = true)
    Iterable<Game> findAllSortedByTitleDescending();

    Iterable<Game> findAllByTitleContainsOrderByPriceAsc(String title);
    Iterable<Game> findAllByTitleContainsOrderByPriceDesc(String title);
    Iterable<Game> findAllByTitleContainsOrderByTitleAsc(String title);
    Iterable<Game> findAllByTitleContainsOrderByTitleDesc(String title);
}
