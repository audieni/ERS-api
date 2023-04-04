package com.audieni.ers.controllers;

import com.audieni.ers.dtos.LoginRequestDto;
import com.audieni.ers.dtos.RegisterRequestDto;
import com.audieni.ers.dtos.UserDto;
import com.audieni.ers.exceptions.InvalidCredentialsException;
import com.audieni.ers.models.User;
import com.audieni.ers.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * User controller to handle HTTP requests
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * User login if credentials are correct
     * @param loginRequestDto - DTO containing user's login credentials
     * @param session - Current HTTP session
     * @return - ResponseEntity with String message
     */
    @PutMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpSession session) {
        try {
            Optional<UserDto> userDto = this.userService.authenticate(loginRequestDto.getEmail(),
                    loginRequestDto.getPassword());
            if (session.getAttribute("user") == null && userDto.isPresent()) {
                session.setAttribute("user", userDto.get());
                return ResponseEntity.ok(userDto.get().toString());
            }
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid credentials.");
        }
        return ResponseEntity.badRequest().body("You're already logged in.");
    }

    /**
     * User logout regardless of logged in status
     * @param session - Current HTTP session
     * @return - ResponseEntity with String message
     */
    @PutMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.removeAttribute("user");
        return ResponseEntity.ok().body("You're logged out.");
    }

    /**
     * User registration if credentials are correct
     * @param registerRequestDto - DTO containing user's registration credentials
     * @return - ResponseEntity with String message
     */
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDto) {
        try {
            User user = new User(registerRequestDto.getUsername(), registerRequestDto.getEmail(),
                registerRequestDto.getPassword(), registerRequestDto.getFirstName(), registerRequestDto.getLastName());
            this.userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new UserDto(user).toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials.");
        }
    }
}
