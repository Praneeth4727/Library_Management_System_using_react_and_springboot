package com.bibliotheca.spring_boot_library.responsemodels;

import com.bibliotheca.spring_boot_library.entity.Book; // Importing the Book entity

import lombok.Data; // Importing Lombok's Data annotation for automatic generation of getters, setters, and other utility methods

/**
 * Response model for current loans on the shelf.
 * This class encapsulates the details of a book that is currently checked out
 * along with the number of days left until it is due.
 */
@Data // Lombok annotation to automatically generate getters, setters, and toString, equals, and hashCode methods
public class ShelfCurrentLoansResponse {
	
	// Constructor to initialize the ShelfCurrentLoansResponse with book details and days left
	public ShelfCurrentLoansResponse(Book book, int daysLeft) {
		this.book = book; // Setting the book details
		this.daysLeft = daysLeft; // Setting the number of days left until the book is due
	}
	
	private Book book; // The Book object representing the currently checked out book
	
	private int daysLeft; // The number of days remaining until the due date for the book
}
