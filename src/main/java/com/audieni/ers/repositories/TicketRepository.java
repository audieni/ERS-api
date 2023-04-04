package com.audieni.ers.repositories;

import com.audieni.ers.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query(value = "SELECT * FROM tickets WHERE user_id = :userId", nativeQuery = true)
    Optional<List<Ticket>> findByUserId(@Param("userId") int userId);

    @Query(value = "SELECT * FROM tickets WHERE LOWER(status) = :status", nativeQuery = true)
    Optional<List<Ticket>> findByStatus(@Param("status") String status);

    @Query(value = "SELECT * FROM tickets WHERE ticket_id = :ticketId AND LOWER(status) = :status", nativeQuery = true)
    Optional<Ticket> findByTicketIdAndStatus(@Param("ticketId") int ticketId, @Param("status") String status);

    @Query(value = "SELECT * FROM tickets WHERE user_id = :userId AND LOWER(status) = :status", nativeQuery = true)
    Optional<List<Ticket>> findByUserIdAndStatus(@Param("userId") int userId, @Param("status") String status);
}
