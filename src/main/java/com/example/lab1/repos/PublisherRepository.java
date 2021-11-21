package com.example.lab1.repos;

import com.example.lab1.model.Game;
import com.example.lab1.model.Publisher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {

    @Query(value = "SELECT * FROM Publishers WHERE publisher_name = :name", nativeQuery = true)
    Publisher findByName(@Param("name") String name);
}
