package com.bibliotheca.spring_boot_library.dao; // Package declaration for the Data Access Object (DAO) layer

import java.util.List; // Importing List for handling collections of checkouts

import com.bibliotheca.spring_boot_library.entity.Checkout; // Importing the Checkout entity

import org.springframework.data.jpa.repository.JpaRepository; // Importing JpaRepository for basic CRUD operations
import org.springframework.data.jpa.repository.Modifying; // Importing Modifying for methods that modify the database
import org.springframework.data.jpa.repository.Query; // Importing Query for custom JPQL queries
import org.springframework.data.repository.query.Param; // Importing Param for named parameters in queries

/**
 * Repository interface for performing CRUD operations on Checkout entities.
 * This interface extends JpaRepository, which provides a set of standard database operations.
 */
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    /**
     * Finds a Checkout record based on the user's email and the book's ID.
     *
     * @param userEmail the email of the user who checked out the book
     * @param bookId    the ID of the book that was checked out
     * @return the Checkout record matching the specified user email and book ID
     */
    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);

    /**
     * Finds all Checkout records associated with a specified user email.
     *
     * @param userEmail the email of the user whose checkouts are to be retrieved
     * @return a list of Checkouts made by the specified user
     */
    List<Checkout> findBooksByUserEmail(String userEmail);

    /**
     * Deletes all Checkout records associated with a specified book ID.
     *
     * @param bookId the ID of the book for which all checkout records should be deleted
     */
    @Modifying // Indicates that this query modifies the database
    @Query("delete from Checkout where book_id in :book_id") // Custom JPQL query to delete checkouts by book ID
    void deleteAllByBookId(@Param("book_id") Long bookId); // Method to delete checkouts based on a specific book ID
}
