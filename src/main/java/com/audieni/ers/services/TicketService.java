package com.audieni.ers.services;

import com.audieni.ers.models.Ticket;
import com.audieni.ers.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Ticket service to handle interactions between the API and the repository
 */
@Service
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * Searches for list of tickets using the user's ID
     * @param userId - User's ID
     * @return - Optional of List of Ticket objects with corresponding user ID
     */
    public Optional<List<Ticket>> findByUserId(int userId) {
        return this.ticketRepository.findByUserId(userId);
    }

    /**
     * Searches for a list of tickets based on the ticket's status
     * @param status - Ticket's status
     * @return - Optional of List of Ticket objects with corresponding ticket status
     */
    public Optional<List<Ticket>> findByStatus(String status) {
        return this.ticketRepository.findByStatus(status);
    }

    /**
     * Searches for a ticket based on the ticket's ID and status
     * @param ticketId - Ticket's ID
     * @param status - Ticket's status
     * @return - Optional of Ticket object with corresponding ticket ID and status
     */
    public Optional<Ticket> findByTicketIdAndStatus(int ticketId, String status) {
        return this.ticketRepository.findByTicketIdAndStatus(ticketId, status);
    }

    /**
     * Searches for a list of tickets based on the user's ID and ticket's status
     * @param userId - User's ID
     * @param status - Ticket's status
     * @return - Optional of List of Ticket objects with corresponding user ID and ticket status
     */
    public Optional<List<Ticket>> findByUserIdAndStatus(int userId, String status) {
        return this.ticketRepository.findByUserIdAndStatus(userId, status);
    }

    /**
     * Searches for a list of all tickets
     * @return - List of all Ticket objects
     */
    public List<Ticket> findAllTickets() {
        return this.ticketRepository.findAll();
    }

    /**
     * Create/update ticket row in database with new ticket information
     * @param ticket - Ticket object
     * @return - Ticket object that was saved to the database
     */
    public Ticket saveTicket(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }
}
