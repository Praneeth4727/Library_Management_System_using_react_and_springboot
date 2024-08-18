package com.bibliotheca.spring_boot_library.controller;

import java.util.List;

import com.bibliotheca.spring_boot_library.entity.Book; // Importing the Book entity
import com.bibliotheca.spring_boot_library.responsemodels.ShelfCurrentLoansResponse; // Importing the response model for current loans
import com.bibliotheca.spring_boot_library.service.BookService; // Importing the book service for business logic
import com.bibliotheca.spring_boot_library.utils.ExtractJWT; // Importing JWT extraction utility

import org.springframework.beans.factory.annotation.Autowired; // Importing Autowired annotation for dependency injection
import org.springframework.web.bind.annotation.CrossOrigin; // Importing CrossOrigin annotation for CORS configuration
import org.springframework.web.bind.annotation.GetMapping; // Importing GetMapping annotation for handling GET requests
import org.springframework.web.bind.annotation.PutMapping; // Importing PutMapping annotation for handling PUT requests
import org.springframework.web.bind.annotation.RequestHeader; // Importing RequestHeader annotation for accessing HTTP headers
import org.springframework.web.bind.annotation.RequestMapping; // Importing RequestMapping annotation for defining request mappings
import org.springframework.web.bind.annotation.RequestParam; // Importing RequestParam annotation for accessing request parameters
import org.springframework.web.bind.annotation.RestController; // Importing RestController annotation to define RESTful controller

@CrossOrigin("https://localhost:3000") // Allowing CORS for the specified origin
@RestController // Indicates that this class is a REST controller
@RequestMapping("/api/books") // Base URL mapping for book-related operations
public class BookController {

    private BookService bookService; // Book service for business logic

    // Constructor for dependency injection of the book service
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    // Endpoint to get the current loans for a user
    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value="Authorization") String token) throws Exception {
        // Extracting the user's email from the JWT token
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        // Returning the list of current loans for the user
        return bookService.currentLoans(userEmail);
    }
    
    // Endpoint to get the count of current loans for a user
    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value="Authorization") String token) {
        // Extracting the user's email from the JWT token
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        // Returning the count of current loans for the user
        return bookService.currentLoansCount(userEmail);
    }
    
    // Endpoint to check if a specific book is checked out by the user
    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader(value="Authorization") String token, @RequestParam Long bookId) {
        // Extracting the user's email from the JWT token
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        // Returning whether the book is checked out by the user
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    // Endpoint to check out a specific book for the user
    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value="Authorization") String token, @RequestParam Long bookId) throws Exception {
        // Extracting the user's email from the JWT token
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        // Checking out the book and returning the book details
        return bookService.checkoutBook(userEmail, bookId);
    }
    
    // Endpoint to return a checked-out book
    @PutMapping("/secure/return")
    public void returnBook(@RequestHeader(value="Authorization") String token,
                           @RequestParam Long bookId) throws Exception {
        // Extracting the user's email from the JWT token
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        // Returning the book via the book service
        bookService.returnBook(userEmail, bookId);
    }
    
    // Endpoint to renew a loan for a specific book
    @PutMapping("/secure/renew/loan")
    public void renewLoan(@RequestHeader(value="Authorization") String token, @RequestParam Long bookId) throws Exception {
        // Extracting the user's email from the JWT token
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        // Renewing the loan for the book via the book service
        bookService.renewLoan(userEmail, bookId);
    }
    
}
