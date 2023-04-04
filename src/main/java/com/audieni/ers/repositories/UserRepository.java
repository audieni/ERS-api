package com.audieni.ers.repositories;

import com.audieni.ers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for interacting with users table of database
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Queries database for a user based on the user's email
     * @param email - User's email address
     * @return - Optional of User object with corresponding email address
     */
    Optional<User> findByEmail(String email);
}
