package com.bibliotheca.spring_boot_library.requestmodels;

import java.util.Optional; // Importing Optional to handle the optional review description
import lombok.Data; // Importing Lombok's Data annotation for automatic generation of getters, setters, and other utility methods

/**
 * Request model for user reviews.
 * This class is used to encapsulate the data required for a user to submit a review
 * for a specific book in the library system.
 */
@Data // Lombok annotation to automatically generate getters, setters, and toString, equals, and hashCode methods
public class ReviewRequest {
    
    private double rating; // The rating given by the user for the book, typically on a scale (e.g., 1 to 5)
    
    private Long bookId; // The ID of the book being reviewed
    
    private Optional<String> reviewDescription; // An optional field for the review text, which may or may not be provided by the user
}
