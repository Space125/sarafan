package com.example.sarafan.repositories;

import com.example.sarafan.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ivan Kurilov on 18.05.2021
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    @EntityGraph(attributePaths = {"comments"})
    Page<Message> findAll(Pageable pageable);
}
