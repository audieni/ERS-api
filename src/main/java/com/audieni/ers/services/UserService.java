package com.audieni.ers.services;

import com.audieni.ers.dtos.UserDto;
import com.audieni.ers.exceptions.InvalidCredentialsException;
import com.audieni.ers.models.User;
import com.audieni.ers.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User service to handle interactions between the API and the repository
 */
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticates user's provided credentials to check if it is valid
     * @param email - User's email address
     * @param password - User's password
     * @return - Optional of UserDto object with the user's password redacted
     * @throws InvalidCredentialsException - Exception thrown if user's provided credentials are invalid
     */
    public Optional<UserDto> authenticate(String email, String password) throws InvalidCredentialsException {
        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return Optional.of(new UserDto(user.get()));
        } else {
            throw new InvalidCredentialsException();
        }
    }

    /**
     * Searches for a user based on the user's email
     * @param email - User's email address
     * @return - Optional of User object with corresponding email address
     */
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    /**
     * Creates/update user row in database with new user information
     * @param user - User object
     * @return - User object that was saved to the database
     */
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }
}
