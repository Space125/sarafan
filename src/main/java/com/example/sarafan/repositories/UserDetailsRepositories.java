package com.example.sarafan.repositories;

import com.example.sarafan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ivan Kurilov on 19.05.2021
 */
public interface UserDetailsRepositories extends JpaRepository<User, String> {

}
