package com.bibliotheca.spring_boot_library.dao; // Package declaration for the Data Access Object (DAO) layer

import com.bibliotheca.spring_boot_library.entity.History; // Importing the History entity

import org.springframework.data.domain.Page; // Importing Page for pagination support
import org.springframework.data.domain.Pageable; // Importing Pageable for pagination parameters
import org.springframework.data.jpa.repository.JpaRepository; // Importing JpaRepository for basic CRUD operations
import org.springframework.web.bind.annotation.RequestParam; // Importing RequestParam for request parameters

/**
 * Repository interface for performing CRUD operations on History entities.
 * This interface extends JpaRepository, which provides a set of standard database operations.
 */
public interface HistoryRepository extends JpaRepository<History, Long> {

    /**
     * Finds the history of books checked out by a specific user.
     *
     * @param userEmail the email of the user whose checkout history is to be retrieved
     * @param pageable   the pagination information (page number, size, etc.)
     * @return a paginated list of History records associated with the specified user email
     */
    Page<History> findBooksByUserEmail(@RequestParam("email") String userEmail, Pageable pageable);
}
