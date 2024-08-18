package com.bibliotheca.spring_boot_library.dao; // Package declaration for the Data Access Object (DAO) layer

import com.bibliotheca.spring_boot_library.entity.Review; // Importing the Review entity

import org.springframework.data.domain.Page; // Importing Page for pagination support
import org.springframework.data.domain.Pageable; // Importing Pageable for specifying pagination parameters
import org.springframework.data.jpa.repository.JpaRepository; // Importing JpaRepository for basic CRUD operations
import org.springframework.data.jpa.repository.Modifying; // Importing Modifying for update/delete operations
import org.springframework.data.jpa.repository.Query; // Importing Query for custom queries
import org.springframework.data.repository.query.Param; // Importing Param for named parameters in queries
import org.springframework.web.bind.annotation.RequestParam; // Importing RequestParam for request parameter mapping

/**
 * Repository interface for performing CRUD operations on Review entities.
 * This interface extends JpaRepository, providing a set of standard database operations.
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Finds a paginated list of reviews for a specific book by its ID.
     *
     * @param bookId the ID of the book whose reviews are to be retrieved
     * @param pageable the pagination information (page number, size, etc.)
     * @return a Page of Review objects associated with the specified book ID
     */
    Page<Review> findByBookId(@RequestParam("book_id") Long bookId, Pageable pageable);

    /**
     * Finds a review by a specific user for a specific book.
     *
     * @param userEmail the email of the user who created the review
     * @param bookId the ID of the book being reviewed
     * @return the Review object associated with the specified user and book, or null if not found
     */
    Review findByUserEmailAndBookId(String userEmail, Long bookId);

    /**
     * Deletes all reviews associated with a specific book ID.
     *
     * @param bookId the ID of the book whose reviews are to be deleted
     */
    @Modifying // Indicates that this method modifies the database
    @Query("delete from Review where book_id in :book_id") // Custom query for deleting reviews
    void deleteAllByBookId(@Param("book_id") Long bookId);
}
