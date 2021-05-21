package com.example.sarafan.repositories;

import com.example.sarafan.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ivan Kurilov on 18.05.2021
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
}
