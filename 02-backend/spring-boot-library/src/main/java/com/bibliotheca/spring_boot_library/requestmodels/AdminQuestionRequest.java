package com.bibliotheca.spring_boot_library.requestmodels;

import lombok.Data; // Importing Lombok's Data annotation for automatic generation of getters, setters, and other utility methods

/**
 * Request model for an administrator's response to a user's question.
 * This class is used to encapsulate the data required for an admin
 * to respond to a user's message or inquiry.
 */
@Data // Lombok annotation to automatically generate getters, setters, and toString, equals, and hashCode methods
public class AdminQuestionRequest {

    private Long id; // The unique identifier of the message or question to which the admin is responding

    private String response; // The response or answer provided by the admin to the user's question
}
