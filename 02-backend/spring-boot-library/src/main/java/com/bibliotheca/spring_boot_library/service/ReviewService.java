package com.bibliotheca.spring_boot_library.service;

import java.sql.Date; // Importing for using SQL Date
import java.time.LocalDate; // Importing for using LocalDate

import com.bibliotheca.spring_boot_library.dao.BookRepository; // Importing BookRepository for managing books (currently unused)
import com.bibliotheca.spring_boot_library.dao.ReviewRepository; // Importing ReviewRepository for managing reviews
import com.bibliotheca.spring_boot_library.entity.Review; // Importing Review entity
import com.bibliotheca.spring_boot_library.requestmodels.ReviewRequest; // Importing request model for review data

import org.springframework.beans.factory.annotation.Autowired; // Importing for dependency injection
import org.springframework.stereotype.Service; // Importing Service to define a service class
import org.springframework.transaction.annotation.Transactional; // Importing Transactional for managing transactions

@Service // Marking this class as a service component
@Transactional // Enabling transaction management for the class
public class ReviewService {
	
	// private BookRepository bookRepository; // Unused variable for BookRepository
	
	private ReviewRepository reviewRepository; // Repository for review database operations
	
	// Constructor-based dependency injection for ReviewRepository
	@Autowired
	public ReviewService(ReviewRepository reviewRepository) {
		// this.bookRepository = bookRepository; // Unused line for initializing BookRepository
		this.reviewRepository = reviewRepository; // Initializing reviewRepository
	}
	
	/**
	 * Posts a review for a book by a user.
	 * 
	 * @param userEmail The email of the user posting the review.
	 * @param reviewRequest The request object containing review details.
	 * @throws Exception if a review has already been created by the user for the book.
	 */
	public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
		// Checking if the user has already created a review for the book
		Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());
		if (validateReview != null) {
			throw new Exception("Review already created"); // Throwing exception if review exists
		}
		
		// Creating a new Review object
		Review review = new Review();
		review.setBookId(reviewRequest.getBookId()); // Setting the book ID
		review.setRating(reviewRequest.getRating()); // Setting the rating from the request
		review.setUserEmail(userEmail); // Setting the user email who posted the review
		
		// Setting the review description if it is present
		if (reviewRequest.getReviewDescription().isPresent()) {
			review.setReviewDescription(reviewRequest.getReviewDescription().map(
			  Object::toString).orElse(null)); // Extracting description or setting it to null
		}
		
		review.setDate(Date.valueOf(LocalDate.now())); // Setting the current date for the review
		reviewRepository.save(review); // Saving the review to the database
	}
	
	/**
	 * Checks if a user has listed a review for a specific book.
	 * 
	 * @param userEmail The email of the user.
	 * @param bookId The ID of the book to check for a review.
	 * @return True if the user has listed a review for the book, false otherwise.
	 */
	public Boolean userReviewListed(String userEmail, Long bookId) {
		// Checking if a review exists for the user and the book
		Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
		if (validateReview != null) {
			return true; // Review exists
		} else {
			return false; // No review found
		}
	}
}
