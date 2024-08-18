package com.bibliotheca.spring_boot_library.entity;

import javax.persistence.Column; // Importing Column for specifying the column details in the database
import javax.persistence.Entity; // Importing Entity to denote that this class is a JPA entity
import javax.persistence.GeneratedValue; // Importing GeneratedValue for auto-incrementing the primary key
import javax.persistence.GenerationType; // Importing GenerationType for defining the strategy of primary key generation
import javax.persistence.Id; // Importing Id to denote the primary key of the entity
import javax.persistence.Table; // Importing Table for specifying the table name in the database

import lombok.Data; // Importing Lombok annotations for generating boilerplate code

/**
 * Represents the history of book checkouts in the library system.
 * This class is mapped to the "History" table in the database and contains details of each book checkout.
 */
@Entity // Denotes that this class is a JPA entity
@Table(name = "History") // Specifies the name of the table in the database
@Data // Lombok annotation to automatically generate getters, setters, and other utility methods
public class History {
	
	// Default constructor for the History entity
	public History() {
	}
	
	/**
	 * Constructor to create a History instance with specified parameters.
	 * 
	 * @param userEmail     the email of the user who checked out the book
	 * @param checkoutDate  the date when the book was checked out
	 * @param returnedDate  the date when the book was returned
	 * @param title         the title of the book
	 * @param author        the author of the book
	 * @param description   a brief description of the book
	 * @param img           the image URL of the book cover
	 */
	public History(String userEmail, String checkoutDate, String returnedDate, String title, String author, String description, String img) {
		this.userEmail = userEmail; // Assigning userEmail to the History record
		this.checkoutDate = checkoutDate; // Assigning checkoutDate to the History record
		this.returnedDate = returnedDate; // Assigning returnedDate to the History record
		this.title = title; // Assigning title to the History record
		this.author = author; // Assigning author to the History record
		this.description = description; // Assigning description to the History record
		this.img = img; // Assigning image URL to the History record
	}

	@Id // Indicates that this field is the primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies that the primary key should be auto-generated
	@Column(name = "id") // Maps this field to the "id" column in the database
	private Long id; // Unique identifier for the history record

	@Column(name = "user_email") // Maps this field to the "user_email" column in the database
	private String userEmail; // Email of the user who checked out the book

	@Column(name = "checkout_date") // Maps this field to the "checkout_date" column in the database
	private String checkoutDate; // The date when the book was checked out

	@Column(name = "returned_date") // Maps this field to the "returned_date" column in the database
	private String returnedDate; // The date when the book was returned

	@Column(name = "title") // Maps this field to the "title" column in the database
	private String title; // Title of the book

	@Column(name = "author") // Maps this field to the "author" column in the database
	private String author; // Author of the book

	@Column(name = "description") // Maps this field to the "description" column in the database
	private String description; // Description of the book

	@Column(name = "img") // Maps this field to the "img" column in the database
	private String img; // Image URL of the book cover
}
