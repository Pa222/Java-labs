package com.example.lab1.repos;

import com.example.lab1.model.Game;
import com.example.lab1.model.Publisher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    @Query(value = "exec GetPublisherByName :name", nativeQuery = true)
    Publisher findByName(@Param("name") String name);

    @Query(value = "exec GetPublishers", nativeQuery = true)
    Iterable<Publisher> getPublishers();

    @Query(value = "exec AddPublisher :name", nativeQuery = true)
    void addPublisher(@Param("name") String name);

    @Query(value = "exec DeletePublisher :id", nativeQuery = true)
    void deletePublisher(@Param("id") Long id);
}
