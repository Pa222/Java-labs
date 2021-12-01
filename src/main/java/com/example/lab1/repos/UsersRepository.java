package com.example.lab1.repos;

import com.example.lab1.dto.GameOrderInfoDto;
import com.example.lab1.model.Game;
import com.example.lab1.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsersRepository  extends CrudRepository<User, Long> {

    @Query(value = "exec GetUserByLogin :login", nativeQuery = true)
    User getByLogin(@Param("login") String Login);

    @Query(value = "exec GetUserOrderGamesIds :order_id, :user_id", nativeQuery = true)
    Iterable<Integer> getUserOrderGamesIds(@Param("order_id") Long orderId, @Param("user_id") Long userId);

    @Query(value = "exec GetUserRolesIds :id", nativeQuery = true)
    Iterable<Integer> getUserRolesId(@Param("id") Long userId);

    @Procedure(name = "Dropuser")
    void createUser(String login, String password, byte[] salt, String name);
}
