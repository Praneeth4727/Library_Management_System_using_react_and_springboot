package com.bibliotheca.spring_boot_library.dao; // Package declaration for the Data Access Object (DAO) layer

import com.bibliotheca.spring_boot_library.entity.Message; // Importing the Message entity

import org.springframework.data.domain.Page; // Importing Page for pagination support
import org.springframework.data.domain.Pageable; // Importing Pageable for pagination parameters
import org.springframework.data.jpa.repository.JpaRepository; // Importing JpaRepository for basic CRUD operations
import org.springframework.web.bind.annotation.RequestParam; // Importing RequestParam for request parameters

/**
 * Repository interface for performing CRUD operations on Message entities.
 * This interface extends JpaRepository, which provides a set of standard database operations.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Finds messages associated with a specific user email.
     *
     * @param userEmail the email of the user whose messages are to be retrieved
     * @param pageable   the pagination information (page number, size, etc.)
     * @return a paginated list of Message records associated with the specified user email
     */
    Page<Message> findByUserEmail(@RequestParam("user_email") String userEmail, Pageable pageable);
    
    /**
     * Finds messages based on their closed status.
     *
     * @param closed   the closed status to filter messages
     * @param pageable  the pagination information (page number, size, etc.)
     * @return a paginated list of Message records with the specified closed status
     */
    Page<Message> findByClosed(@RequestParam("closed") boolean closed, Pageable pageable);
}
