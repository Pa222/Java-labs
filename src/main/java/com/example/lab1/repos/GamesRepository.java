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

    @Procedure(value = "UpdateGameInfo")
    void updateGame(Long id, Long publisher_id, String title, String rating, float price, String game_description);

    @Procedure(value = "DeleteGame")
    void deleteGame(Long gameId);

    @Query(value = "exec GetGamesByPageNumber :page, :size, :title", nativeQuery = true)
    Iterable<Game> getGamesByPageNumber(@Param("page") int page, @Param("size") int size, @Param("title") String title);

    @Query(value = "exec GetGames", nativeQuery = true)
    Iterable<Game> getGames();

    @Query(value = "exec GetGamesCount", nativeQuery = true)
    int getGamesCount();

    @Query(value = "exec GetGamesByTitleCount :title", nativeQuery = true)
    int getGamesByTitleCount(@Param("title") String title);

    @Query(value = "exec GetGameById :id", nativeQuery = true)
    Game getGameById(@Param("id") Long id);
}
