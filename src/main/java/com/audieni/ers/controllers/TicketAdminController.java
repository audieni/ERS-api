package com.audieni.ers.controllers;

import com.audieni.ers.dtos.UserDto;
import com.audieni.ers.models.Ticket;
import com.audieni.ers.services.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TicketAdmin controller to handle HTTP requests
 */
@RestController
@RequestMapping("/api/admin/tickets")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class TicketAdminController {
    private final TicketService ticketService;

    @Autowired
    public TicketAdminController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Searches for a list of all pending tickets
     * @param session - Current HTTP session
     * @return - Response with List of pending Ticket objects belonging to any user
     */
    @GetMapping
    public ResponseEntity<List<Ticket>> findPendingTickets(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        if (session.getAttribute("user") != null && userDto.isManager()) {
            Optional<List<Ticket>> ticketList = this.ticketService.findByStatus("pending");
            return ticketList.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(new ArrayList<>()));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Searches for a list of all tickets regardless of status
     * @param session - Current HTTP session
     * @return - Response with List of Ticket objects belonging to any user
     */
    @GetMapping(value = "/all")
    public ResponseEntity<List<Ticket>> findAllTickets(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        if (session.getAttribute("user") != null && userDto.isManager()) {
            List<Ticket> ticketList = this.ticketService.findAllTickets();
            return ResponseEntity.ok(ticketList);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Searches for a list of all tickets with the corresponding status
     * @param session - Current HTTP session
     * @param status - Ticket's status
     * @return - Response with List of Ticket objects based on status
     */
    @GetMapping(value = "/{status}")
    public ResponseEntity<List<Ticket>> findTicketsByStatus(HttpSession session, @PathVariable String status) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        if (session.getAttribute("user") != null && userDto.isManager()) {
            Optional<List<Ticket>> ticketList = this.ticketService.findByStatus(status);
            return ticketList.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok(new ArrayList<>()));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the status of pending ticket based on ID
     * @param session - Current HTTP session
     * @param status - Ticket's status
     * @param id - Ticket's ID
     * @return - Response with Ticket object containing updated status
     */
    @PutMapping(value = "/{id}/{status}")
    public ResponseEntity<Ticket> updateTicketStatus(HttpSession session, @PathVariable String status,
                                                     @PathVariable int id) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        if (session.getAttribute("user") == null || !userDto.isManager()) {
            return null;
        }
        Optional<Ticket> ticket = this.ticketService.findByTicketIdAndStatus(id, "pending");
        if (ticket.isPresent()) {
            ticket.get().setStatus(status);
            return ResponseEntity.ok(this.ticketService.saveTicket(ticket.get()));
        }
        return ResponseEntity.badRequest().build();
    }
}
