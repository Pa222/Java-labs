package com.example.lab1.repos;

import com.example.lab1.model.Game;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface GamesRepository extends CrudRepository<Game, Long>{
    @Procedure(value = "AddGame")
    void addNewGame(Long publisher_id, String title, String rating, float price, String game_description);

//    @Modifying
//    @Query(value = "update Games set publisher_id = :publisher, title = :title, rating = :rating, " +
//            " price = :price, game_description = :gameDescription where id = :id", nativeQuery = true)
//    @Transactional
//    void updateGame(
//                    @Param("id") Long id,
//                    @Param("publisher") Long publisher,
//                    @Param("title") String title,
//                    @Param("rating") String rating,
//                    @Param("price") float price,
//                    @Param("gameDescription") String gameDescription
//    );

    @Procedure(value = "UpdateGameInfo")
    void updateGame(Long id, Long publisher_id, String title, String rating, float price, String game_description);

    @Procedure(value = "DeleteGame")
    void deleteGame(Long gameId);

    @Query(value = "exec GetGamesByPriceAscending", nativeQuery = true)
    Iterable<Game> getGamesByPriceAscending();

    @Query(value = "exec GetGamesByPriceDescending", nativeQuery = true)
    Iterable<Game> getGamesByPriceDescending();

    @Query(value = "exec GetGamesByTitleAscending", nativeQuery = true)
    Iterable<Game> getGamesByTitleAscending();

    @Query(value = "exec GetGamesByTitleDescending", nativeQuery = true)
    Iterable<Game> getGamesByTitleDescending();

    @Query(value = "exec GetGamesByPriceAscendingTitleContains :contain", nativeQuery = true)
    Iterable<Game> getGamesByPriceAscendingTitleContains(@Param("contain") String contain);

    @Query(value = "exec GetGamesByPriceDescendingTitleContains :contain", nativeQuery = true)
    Iterable<Game> getGamesByPriceDescendingTitleContains(@Param("contain") String contain);

    @Query(value = "exec GetGamesByTitleAscendingTitleContains :contain", nativeQuery = true)
    Iterable<Game> getGamesByTitleAscendingTitleContains(@Param("contain") String contain);

    @Query(value = "exec GetGamesByTitleDescendingTitleContains :contain", nativeQuery = true)
    Iterable<Game> getGamesByTitleDescendingTitleContains(@Param("contain") String contain);
}
