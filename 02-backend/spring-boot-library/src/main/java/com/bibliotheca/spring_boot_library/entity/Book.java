package com.bibliotheca.spring_boot_library.entity;

import javax.persistence.Column; // Importing Column for specifying the column details in the database
import javax.persistence.Entity; // Importing Entity to denote that this class is a JPA entity
import javax.persistence.GeneratedValue; // Importing GeneratedValue for auto-incrementing the primary key
import javax.persistence.GenerationType; // Importing GenerationType for defining the strategy of primary key generation
import javax.persistence.Id; // Importing Id to denote the primary key of the entity
import javax.persistence.Table; // Importing Table for specifying the table name in the database

import lombok.*; // Importing Lombok annotations for generating boilerplate code

/**
 * Represents a book entity in the library system.
 * This class is mapped to the "book" table in the database and defines the attributes of a book.
 */
@Entity // Denotes that this class is a JPA entity
@Table(name = "book") // Specifies the name of the table in the database
@Data // Lombok annotation to automatically generate getters, setters, and other utility methods
public class Book {

    @Id // Indicates that this field is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies that the primary key should be auto-generated
    @Column(name = "id") // Maps this field to the "id" column in the database
    private Long id;

    @Column(name = "title") // Maps this field to the "title" column in the database
    private String title;

    @Column(name = "author") // Maps this field to the "author" column in the database
    private String author;

    @Column(name = "description") // Maps this field to the "description" column in the database
    private String description;

    @Column(name = "copies") // Maps this field to the "copies" column in the database
    private int copies; // Total number of copies of the book available in the library

    @Column(name = "copies_available") // Maps this field to the "copies_available" column in the database
    private int copiesAvailable; // Number of copies currently available for checkout

    @Column(name = "category") // Maps this field to the "category" column in the database
    private String category; // The category or genre of the book

    @Column(name = "img") // Maps this field to the "img" column in the database
    private String img; // URL or path of the book's cover image
}
