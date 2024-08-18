package com.bibliotheca.spring_boot_library.requestmodels;

import lombok.Data; // Importing Lombok's Data annotation for automatic generation of getters, setters, and other utility methods

/**
 * Request model for adding a new book to the library.
 * This class encapsulates the data required to create a new book entry.
 */
@Data // Lombok annotation to automatically generate getters, setters, and toString, equals, and hashCode methods
public class AddBookRequest {

    private String title; // The title of the book

    private String author; // The author of the book

    private String description; // A brief description of the book

    private int copies; // The total number of copies of the book available in the library

    private String category; // The category or genre of the book

    private String img; // The URL or path to the book's cover image
}
