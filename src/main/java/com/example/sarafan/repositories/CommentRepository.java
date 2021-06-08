package com.example.sarafan.repositories;

import com.example.sarafan.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ivan Kurilov on 07.06.2021
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
