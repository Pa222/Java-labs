package com.example.lab1.repos;

import com.example.lab1.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsersRepository  extends CrudRepository<User, Long> {

    User findByLogin(String Login);
}
