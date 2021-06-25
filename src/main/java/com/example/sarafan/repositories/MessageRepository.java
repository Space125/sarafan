package com.example.sarafan.repositories;

import com.example.sarafan.domain.Message;
import com.example.sarafan.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ivan Kurilov on 18.05.2021
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    @EntityGraph(attributePaths = {"comments"})
    Page<Message> findByAuthorIn(List<User> users, Pageable pageable);
}
