package com.audieni.ers.controllers;

import com.audieni.ers.dtos.UserDto;
import com.audieni.ers.models.Ticket;
import com.audieni.ers.models.User;
import com.audieni.ers.services.TicketService;
import com.audieni.ers.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Ticket controller to handle HTTP requests
 */
@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class TicketController {
    private final UserService userService;
    private final TicketService ticketService;

    @Autowired
    public TicketController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    /**
     * Searches for a list of the user's pending tickets
     * @param session - Current HTTP session
     * @return - Response with List of Ticket objects belonging to the user
     */
    @GetMapping
    public ResponseEntity<List<Ticket>> findPendingTicketsByUserId(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        Optional<User> user = this.userService.findByEmail(userDto.getEmail());
        if (session.getAttribute("user") != null && user.isPresent()) {
            Optional<List<Ticket>> ticketList = this.ticketService.findByUserIdAndStatus(user.get().getUserId(),
                    "pending");
            return ticketList.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(new ArrayList<>()));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Searches for a list of all the user's tickets
     * @param session - Current HTTP session
     * @return - Response with List of Ticket objects belonging to the user
     */
    @GetMapping(value = "/all")
    public ResponseEntity<List<Ticket>> findTicketsByUserId(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        Optional<User> user = this.userService.findByEmail(userDto.getEmail());
        if (session.getAttribute("user") != null && user.isPresent()) {
            Optional<List<Ticket>> ticketList = this.ticketService.findByUserId(user.get().getUserId());
            return ticketList.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(new ArrayList<>()));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Searches for a list of user's tickets based on its status
     * @param session - Current HTTP session
     * @param status - Ticket's status
     * @return - Response with List of Ticket objects based on status and belonging to the user
     */
    @GetMapping(value = "/{status}")
    public ResponseEntity<List<Ticket>> findTicketsByUserIdAndStatus(HttpSession session, @PathVariable String status) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        Optional<User> user = this.userService.findByEmail(userDto.getEmail());
        if (session.getAttribute("user") != null && user.isPresent()) {
            Optional<List<Ticket>> ticketList = this.ticketService.findByUserIdAndStatus(user.get().getUserId(),
                    status);
            return ticketList.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(new ArrayList<>()));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Create a new ticket based on information in request body
     * @param session - Current HTTP session
     * @param ticket - Ticket object
     * @return - Response with Ticket that was created in the database
     */
    @PostMapping(value = "/submit")
    public ResponseEntity<Ticket> createTicket(HttpSession session, @RequestBody Ticket ticket) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        Optional<User> user = this.userService.findByEmail(userDto.getEmail());
        if (session.getAttribute("user") != null && user.isPresent()) {
            ticket.setUser(user.get());
            return ResponseEntity.ok(this.ticketService.saveTicket(ticket));
        }
        return ResponseEntity.badRequest().build();
    }
}
