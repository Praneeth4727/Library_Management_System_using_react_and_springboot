package com.bibliotheca.spring_boot_library.entity;

import javax.persistence.Column; // Importing Column for specifying the column details in the database
import javax.persistence.Entity; // Importing Entity to denote that this class is a JPA entity
import javax.persistence.GeneratedValue; // Importing GeneratedValue for auto-incrementing the primary key
import javax.persistence.GenerationType; // Importing GenerationType for defining the strategy of primary key generation
import javax.persistence.Id; // Importing Id to denote the primary key of the entity
import javax.persistence.Table; // Importing Table for specifying the table name in the database

import lombok.Data; // Importing Lombok annotations for generating boilerplate code

/**
 * Represents a checkout record in the library system.
 * This class is mapped to the "checkout" table in the database and defines the attributes of a book checkout.
 */
@Entity // Denotes that this class is a JPA entity
@Table(name = "checkout") // Specifies the name of the table in the database
@Data // Lombok annotation to automatically generate getters, setters, and other utility methods
public class Checkout {
	
	// Default constructor for the Checkout entity
	public Checkout() {
	}
	
	/**
	 * Constructor to create a Checkout instance with specified parameters.
	 * 
	 * @param userEmail    the email of the user who checked out the book
	 * @param checkoutDate the date when the book was checked out
	 * @param returnDate   the date when the book is due to be returned
	 * @param bookId      the ID of the book being checked out
	 */
	public Checkout(String userEmail, String checkoutDate, String returnDate, Long bookId) {
		this.userEmail = userEmail; // Assigning userEmail to the Checkout record
		this.checkoutDate = checkoutDate; // Assigning checkoutDate to the Checkout record
		this.returnDate = returnDate; // Assigning returnDate to the Checkout record
		this.bookId = bookId; // Assigning bookId to the Checkout record
	}
	
	@Id // Indicates that this field is the primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies that the primary key should be auto-generated
	@Column(name = "id") // Maps this field to the "id" column in the database
	private Long id;

	@Column(name = "user_email") // Maps this field to the "user_email" column in the database
	private String userEmail; // Email of the user who checked out the book

	@Column(name = "checkout_date") // Maps this field to the "checkout_date" column in the database
	private String checkoutDate; // The date when the book was checked out

	@Column(name = "return_date") // Maps this field to the "return_date" column in the database
	private String returnDate; // The date when the book is due to be returned

	@Column(name = "book_id") // Maps this field to the "book_id" column in the database
	private Long bookId; // ID of the book being checked out
}
