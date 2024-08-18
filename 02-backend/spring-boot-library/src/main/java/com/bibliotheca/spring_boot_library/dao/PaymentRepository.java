package com.bibliotheca.spring_boot_library.dao; // Package declaration for the Data Access Object (DAO) layer

import com.bibliotheca.spring_boot_library.entity.Payment; // Importing the Payment entity

import org.springframework.data.jpa.repository.JpaRepository; // Importing JpaRepository for basic CRUD operations

/**
 * Repository interface for performing CRUD operations on Payment entities.
 * This interface extends JpaRepository, which provides a set of standard database operations.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Finds a Payment record associated with a specific user email.
     *
     * @param userEmail the email of the user whose payment information is to be retrieved
     * @return the Payment record associated with the specified user email, or null if no record is found
     */
    Payment findByUserEmail(String userEmail);
}
