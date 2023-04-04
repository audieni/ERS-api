package com.audieni.ers.services;

import com.audieni.ers.dtos.UserDto;
import com.audieni.ers.exceptions.InvalidCredentialsException;
import com.audieni.ers.models.User;
import com.audieni.ers.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserDto> authenticate(String email, String password) throws InvalidCredentialsException {
        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return Optional.of(new UserDto(user.get()));
        } else {
            throw new InvalidCredentialsException();
        }
    }

    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return this.userRepository.save(user);
    }
}
