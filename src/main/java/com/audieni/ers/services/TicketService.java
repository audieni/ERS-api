package com.audieni.ers.services;

import com.audieni.ers.models.Ticket;
import com.audieni.ers.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Optional<List<Ticket>> findByUserId(int userId) {
        return this.ticketRepository.findByUserId(userId);
    }

    public Optional<List<Ticket>> findByStatus(String status) {
        return this.ticketRepository.findByStatus(status);
    }

    public Optional<Ticket> findByTicketIdAndStatus(int ticketId, String status) {
        return this.ticketRepository.findByTicketIdAndStatus(ticketId, status);
    }

    public Optional<List<Ticket>> findByUserIdAndStatus(int userId, String status) {
        return this.ticketRepository.findByUserIdAndStatus(userId, status);
    }

    public List<Ticket> findAllTickets() {
        return this.ticketRepository.findAll();
    }

    public Ticket saveTicket(Ticket ticket) {
        return this.ticketRepository.save(ticket);
    }
}
