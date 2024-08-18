package com.bibliotheca.spring_boot_library.entity;

import lombok.Data; // Importing Lombok for generating boilerplate code automatically

import javax.persistence.*; // Importing JPA annotations for defining the entity and its properties

/**
 * Represents a message or question submitted by users in the library system.
 * This class is mapped to the "messages" table in the database and contains details of the user's inquiry.
 */
@Entity // Denotes that this class is a JPA entity
@Table(name = "messages") // Specifies the name of the table in the database
@Data // Lombok annotation to automatically generate getters, setters, and other utility methods
public class Message {

    // Default constructor for the Message entity
    public Message() {}

    /**
     * Constructor to create a Message instance with specified parameters.
     *
     * @param title   the title of the message
     * @param question the content of the question asked by the user
     */
    public Message(String title, String question) {
        this.title = title; // Assigning title to the message
        this.question = question; // Assigning question to the message
    }

    @Id // Indicates that this field is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies that the primary key should be auto-generated
    @Column(name = "id") // Maps this field to the "id" column in the database
    private Long id; // Unique identifier for the message

    @Column(name = "user_email") // Maps this field to the "user_email" column in the database
    private String userEmail; // Email of the user who submitted the message

    @Column(name = "title") // Maps this field to the "title" column in the database
    private String title; // Title of the message

    @Column(name = "question") // Maps this field to the "question" column in the database
    private String question; // Content of the question asked by the user

    @Column(name = "admin_email") // Maps this field to the "admin_email" column in the database
    private String adminEmail; // Email of the admin responding to the message

    @Column(name = "response") // Maps this field to the "response" column in the database
    private String response; // Response provided by the admin to the user's question

    @Column(name = "closed") // Maps this field to the "closed" column in the database
    private boolean closed; // Indicates whether the message has been closed or resolved
}
