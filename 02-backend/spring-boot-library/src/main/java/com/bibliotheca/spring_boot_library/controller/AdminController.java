package com.bibliotheca.spring_boot_library.controller;

import com.bibliotheca.spring_boot_library.requestmodels.AddBookRequest; // Importing the model for adding a book
import com.bibliotheca.spring_boot_library.service.AdminService; // Importing the admin service for business logic
import com.bibliotheca.spring_boot_library.utils.ExtractJWT; // Importing JWT extraction utility

import org.springframework.beans.factory.annotation.Autowired; // Importing Autowired annotation for dependency injection
import org.springframework.web.bind.annotation.CrossOrigin; // Importing CrossOrigin annotation for CORS configuration
import org.springframework.web.bind.annotation.DeleteMapping; // Importing DeleteMapping annotation for handling DELETE requests
import org.springframework.web.bind.annotation.PostMapping; // Importing PostMapping annotation for handling POST requests
import org.springframework.web.bind.annotation.PutMapping; // Importing PutMapping annotation for handling PUT requests
import org.springframework.web.bind.annotation.RequestBody; // Importing RequestBody annotation for mapping request body to method parameter
import org.springframework.web.bind.annotation.RequestHeader; // Importing RequestHeader annotation for accessing HTTP headers
import org.springframework.web.bind.annotation.RequestMapping; // Importing RequestMapping annotation for defining request mappings
import org.springframework.web.bind.annotation.RequestParam; // Importing RequestParam annotation for accessing request parameters
import org.springframework.web.bind.annotation.RestController; // Importing RestController annotation to define RESTful controller

@CrossOrigin("https://localhost:3000") // Allowing CORS for the specified origin
@RestController // Indicates that this class is a REST controller
@RequestMapping("/api/admin") // Base URL mapping for admin operations
public class AdminController {

    private AdminService adminService; // Admin service for business logic

    // Constructor for dependency injection of the admin service
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Endpoint to increase the quantity of a specific book
    @PutMapping("/secure/increase/book/quantity")
    public void increaseBookQuantity(@RequestHeader(value="Authorization") String token,
                                     @RequestParam Long bookId) throws Exception {
        // Extracting the admin role from the JWT token
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        // Checking if the user is an admin
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only"); // Throwing exception if not an admin
        }
        // Increasing the book quantity via the admin service
        adminService.increaseBookQuantity(bookId);
    }

    // Endpoint to decrease the quantity of a specific book
    @PutMapping("/secure/decrease/book/quantity")
    public void decreaseBookQuantity(@RequestHeader(value="Authorization") String token,
                                     @RequestParam Long bookId) throws Exception {
        // Extracting the admin role from the JWT token
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        // Checking if the user is an admin
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only"); // Throwing exception if not an admin
        }
        // Decreasing the book quantity via the admin service
        adminService.decreaseBookQuantity(bookId);
    }

    // Endpoint to add a new book
    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value="Authorization") String token,
                         @RequestBody AddBookRequest addBookRequest) throws Exception {
        // Extracting the admin role from the JWT token
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        // Checking if the user is an admin
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only"); // Throwing exception if not an admin
        }
        // Adding the new book via the admin service
        adminService.postBook(addBookRequest);
    }

    // Endpoint to delete a specific book
    @DeleteMapping("/secure/delete/book")
    public void deleteBook(@RequestHeader(value="Authorization") String token,
                           @RequestParam Long bookId) throws Exception {
        // Extracting the admin role from the JWT token
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        // Checking if the user is an admin
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only"); // Throwing exception if not an admin
        }
        // Deleting the book via the admin service
        adminService.deleteBook(bookId);
    }
}
