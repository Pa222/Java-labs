package com.example.lab1.repos;

import com.example.lab1.model.Game;
import com.example.lab1.model.Publisher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    @Query(value = "exec GetPublisherByName :name", nativeQuery = true)
    Publisher findByName(@Param("name") String name);

    @Query(value = "exec GetPublisherById :id", nativeQuery = true)
    Publisher getById(@Param("id") Long id);

    @Query(value = "exec GetPublishers", nativeQuery = true)
    Iterable<Publisher> getPublishers();

    @Query(value = "exec GetPublishersByPageNumber :page, :size", nativeQuery = true)
    Iterable<Publisher> getPublishersByPageNumber(@Param("page") int page, @Param("size") int size);

    @Procedure(value = "AddPublisher")
    void addPublisher(String name);

    @Procedure(value = "DeletePublisher")
    void deletePublisher(Long id);

    @Procedure(value = "EditPublisher")
    void editPublisher(Long id, String name);

    @Query(value = "exec GetPublishersCount", nativeQuery = true)
    int getPublisherCount();
}
