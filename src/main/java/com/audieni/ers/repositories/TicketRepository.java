package com.audieni.ers.repositories;

import com.audieni.ers.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for interacting with tickets table of database
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    /**
     * Queries database for a list of tickets based on the user's ID
     * @param userId - User's ID
     * @return - Optional of List of Ticket objects with corresponding user ID
     */
    @Query(value = "SELECT * FROM tickets WHERE user_id = :userId", nativeQuery = true)
    Optional<List<Ticket>> findByUserId(@Param("userId") int userId);

    /**
     * Queries database for a list of tickets based on the ticket's status
     * @param status - Ticket's status
     * @return - Optional of List of Ticket objects with corresponding ticket status
     */
    @Query(value = "SELECT * FROM tickets WHERE LOWER(status) = :status", nativeQuery = true)
    Optional<List<Ticket>> findByStatus(@Param("status") String status);

    /**
     * Queries database for a ticket based on the ticket's ID and status
     * @param ticketId - Ticket's ID
     * @param status - Ticket's status
     * @return - Optional of Ticket object with corresponding ticket ID and status
     */
    @Query(value = "SELECT * FROM tickets WHERE ticket_id = :ticketId AND LOWER(status) = :status", nativeQuery = true)
    Optional<Ticket> findByTicketIdAndStatus(@Param("ticketId") int ticketId, @Param("status") String status);

    /**
     * Queries database for a list of tickets based on the user's ID and ticket's status
     * @param userId - User's ID
     * @param status - Ticket's status
     * @return - Optional of List of Ticket objects with corresponding user ID and ticket status
     */
    @Query(value = "SELECT * FROM tickets WHERE user_id = :userId AND LOWER(status) = :status", nativeQuery = true)
    Optional<List<Ticket>> findByUserIdAndStatus(@Param("userId") int userId, @Param("status") String status);
}
