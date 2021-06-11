package com.example.sarafan.repositories;

import com.example.sarafan.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Ivan Kurilov on 19.05.2021
 */
public interface UserDetailsRepositories extends JpaRepository<User, String> {
    @Override
    @EntityGraph(attributePaths = {"subscriptions", "subscribers"})
    Optional<User> findById(String s);
}
