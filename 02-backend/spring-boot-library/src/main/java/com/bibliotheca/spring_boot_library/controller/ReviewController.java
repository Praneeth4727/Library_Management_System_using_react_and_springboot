package com.bibliotheca.spring_boot_library.controller;

import com.bibliotheca.spring_boot_library.requestmodels.ReviewRequest; // Importing the request model for reviews
import com.bibliotheca.spring_boot_library.service.ReviewService; // Importing the service for handling review operations
import com.bibliotheca.spring_boot_library.utils.ExtractJWT; // Importing utility for extracting information from JWT

import org.springframework.web.bind.annotation.CrossOrigin; // Importing annotation for CORS configuration
import org.springframework.web.bind.annotation.GetMapping; // Importing annotation for handling GET requests
import org.springframework.web.bind.annotation.PostMapping; // Importing annotation for handling POST requests
import org.springframework.web.bind.annotation.RequestBody; // Importing annotation for accessing request bodies
import org.springframework.web.bind.annotation.RequestHeader; // Importing annotation for accessing HTTP headers
import org.springframework.web.bind.annotation.RequestMapping; // Importing annotation for defining request mappings
import org.springframework.web.bind.annotation.RequestParam; // Importing annotation for accessing request parameters
import org.springframework.web.bind.annotation.RestController; // Importing annotation to define RESTful controller

@CrossOrigin("https://localhost:3000") // Allowing CORS for the specified origin
@RestController // Indicates that this class is a REST controller
@RequestMapping("/api/reviews") // Base URL mapping for review-related operations
public class ReviewController {
	
	private ReviewService reviewService; // Service for handling review-related logic
	
	// Constructor for dependency injection of the review service
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	// Endpoint to check if a user has reviewed a specific book
	@GetMapping("/secure/user/book")
	public Boolean reviewBookByUser(@RequestHeader(value="Authorization") String token, @RequestParam Long bookId) throws Exception {
		// Extracting the user's email from the JWT token
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		// Checking if the user email was successfully extracted
		if(userEmail == null) {
			throw new Exception("user email is missing!"); // Throwing an exception if email is missing
		}
		
		// Calling the service to check if the user has already reviewed the book
		return reviewService.userReviewListed(userEmail, bookId);
	}
	
	// Endpoint for posting a review for a book
	@PostMapping("/secure")
	public void postReview(@RequestHeader(value="Authorization") String token,
			@RequestBody ReviewRequest reviewRequest) throws Exception {
		// Extracting the user's email from the JWT token
		String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
		// Checking if the user email was successfully extracted
		if(userEmail == null) {
			throw new Exception("user email is missing!"); // Throwing an exception if email is missing
		}
		// Calling the service to post the review for the user
		reviewService.postReview(userEmail, reviewRequest);
	}
}
