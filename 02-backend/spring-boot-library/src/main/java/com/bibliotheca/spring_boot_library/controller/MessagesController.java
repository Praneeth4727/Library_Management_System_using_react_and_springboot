package com.bibliotheca.spring_boot_library.controller;

import com.bibliotheca.spring_boot_library.entity.Message; // Importing the Message entity
import com.bibliotheca.spring_boot_library.requestmodels.AdminQuestionRequest; // Importing the request model for admin questions
import com.bibliotheca.spring_boot_library.service.MessagesService; // Importing the service for handling message operations
import com.bibliotheca.spring_boot_library.utils.ExtractJWT; // Importing JWT extraction utility

import org.springframework.beans.factory.annotation.Autowired; // Importing Autowired annotation for dependency injection
import org.springframework.web.bind.annotation.CrossOrigin; // Importing CrossOrigin annotation for CORS configuration
import org.springframework.web.bind.annotation.PostMapping; // Importing PostMapping annotation for handling POST requests
import org.springframework.web.bind.annotation.PutMapping; // Importing PutMapping annotation for handling PUT requests
import org.springframework.web.bind.annotation.RequestBody; // Importing RequestBody annotation for accessing request bodies
import org.springframework.web.bind.annotation.RequestHeader; // Importing RequestHeader annotation for accessing HTTP headers
import org.springframework.web.bind.annotation.RequestMapping; // Importing RequestMapping annotation for defining request mappings
import org.springframework.web.bind.annotation.RestController; // Importing RestController annotation to define RESTful controller

@CrossOrigin("https://localhost:3000") // Allowing CORS for the specified origin
@RestController // Indicates that this class is a REST controller
@RequestMapping("/api/messages") // Base URL mapping for message-related operations
public class MessagesController {

    private MessagesService messagesService; // Service for handling message-related logic

    // Constructor for dependency injection of the messages service
    @Autowired
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    // Endpoint for adding a new message
    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value="Authorization") String token,
                            @RequestBody Message messageRequest) {
        // Extracting the user's email from the JWT token
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        // Calling the service to post the message with the user's email
        messagesService.postMessage(messageRequest, userEmail);
    }

    // Endpoint for an admin to respond to a message
    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value="Authorization") String token,
                           @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {
        // Extracting the user's email from the JWT token
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        // Extracting the user type from the JWT token to verify admin status
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        // Checking if the user is an admin; if not, throw an exception
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only.");
        }
        // Calling the service to update the message with the admin's response
        messagesService.putMessage(adminQuestionRequest, userEmail);
    }

}
