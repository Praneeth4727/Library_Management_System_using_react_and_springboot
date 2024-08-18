package com.bibliotheca.spring_boot_library.service;

import java.util.Optional; // Importing for handling optional values

import com.bibliotheca.spring_boot_library.dao.MessageRepository; // Importing MessageRepository for managing messages
import com.bibliotheca.spring_boot_library.entity.Message; // Importing Message entity
import com.bibliotheca.spring_boot_library.requestmodels.AdminQuestionRequest; // Importing request model for admin responses

import org.springframework.beans.factory.annotation.Autowired; // Importing for dependency injection
import org.springframework.stereotype.Service; // Importing Service to define a service class
import org.springframework.transaction.annotation.Transactional; // Importing Transactional for managing transactions

@Service // Marking this class as a service component
@Transactional // Enabling transaction management for the class
public class MessagesService {

    private MessageRepository messageRepository; // Repository for message database operations

    // Constructor-based dependency injection for the MessageRepository
    @Autowired
    public MessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository; // Initializing messageRepository
    }

    /**
     * Posts a new message from a user.
     * 
     * @param messageRequest The message object containing the title and question.
     * @param userEmail The email of the user posting the message.
     */
    public void postMessage(Message messageRequest, String userEmail) {
        // Creating a new Message object with the title and question from the request
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail); // Setting the user email for the message
        messageRepository.save(message); // Saving the message to the database
    }

    /**
     * Updates an existing message with an admin's response.
     * 
     * @param adminQuestionRequest The request object containing the message ID and response.
     * @param userEmail The email of the admin responding to the message.
     * @throws Exception if the message is not found.
     */
    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) throws Exception {
        // Fetching the message by ID
        Optional<Message> message = messageRepository.findById(adminQuestionRequest.getId());
        if (!message.isPresent()) {
            throw new Exception("Message not found"); // Throwing exception if the message doesn't exist
        }

        // Updating the message with admin's email and response
        message.get().setAdminEmail(userEmail); // Setting the admin email for the response
        message.get().setResponse(adminQuestionRequest.getResponse()); // Setting the admin's response
        message.get().setClosed(true); // Marking the message as closed
        messageRepository.save(message.get()); // Saving the updated message to the database
    }
}
