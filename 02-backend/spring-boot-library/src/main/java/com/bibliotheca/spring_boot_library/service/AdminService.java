package com.bibliotheca.spring_boot_library.service;

import java.util.Optional; // Importing Optional for handling null values

import com.bibliotheca.spring_boot_library.dao.BookRepository; // Importing BookRepository for database operations on books
import com.bibliotheca.spring_boot_library.dao.CheckoutRepository; // Importing CheckoutRepository for managing checkouts
import com.bibliotheca.spring_boot_library.dao.ReviewRepository; // Importing ReviewRepository for managing reviews
import com.bibliotheca.spring_boot_library.entity.Book; // Importing Book entity
import com.bibliotheca.spring_boot_library.requestmodels.AddBookRequest; // Importing request model for adding a book

import org.springframework.beans.factory.annotation.Autowired; // Importing Autowired for dependency injection
import org.springframework.stereotype.Service; // Importing Service to define a service class
import org.springframework.transaction.annotation.Transactional; // Importing Transactional for handling transactions

@Service // Marking this class as a service component
@Transactional // Enabling transaction management for the class
public class AdminService {

    // Repositories for database operations
    private BookRepository bookRepository;
    private ReviewRepository reviewRepository;
    private CheckoutRepository checkoutRepository;

    // Constructor-based dependency injection
    @Autowired
    public AdminService (BookRepository bookRepository,
                         ReviewRepository reviewRepository,
                         CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository; // Initializing bookRepository
        this.reviewRepository = reviewRepository; // Initializing reviewRepository
        this.checkoutRepository = checkoutRepository; // Initializing checkoutRepository
    }

    /**
     * Increases the quantity of a specific book.
     * 
     * @param bookId The ID of the book whose quantity is to be increased.
     * @throws Exception if the book is not found.
     */
    public void increaseBookQuantity(Long bookId) throws Exception {
        // Fetching the book by ID
        Optional<Book> book = bookRepository.findById(bookId);

        // Checking if the book is present
        if (!book.isPresent()) {
            throw new Exception("Book not found"); // Throwing exception if book not found
        }

        // Incrementing the available and total copies of the book
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
        book.get().setCopies(book.get().getCopies() + 1);

        // Saving the updated book back to the repository
        bookRepository.save(book.get());
    }

    /**
     * Decreases the quantity of a specific book.
     * 
     * @param bookId The ID of the book whose quantity is to be decreased.
     * @throws Exception if the book is not found or if the quantity is locked.
     */
    public void decreaseBookQuantity(Long bookId) throws Exception {
        // Fetching the book by ID
        Optional<Book> book = bookRepository.findById(bookId);

        // Checking if the book is present and has available copies
        if (!book.isPresent() || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0) {
            throw new Exception("Book not found or quantity locked"); // Throwing exception if conditions are not met
        }

        // Decrementing the available and total copies of the book
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        book.get().setCopies(book.get().getCopies() - 1);

        // Saving the updated book back to the repository
        bookRepository.save(book.get());
    }

    /**
     * Adds a new book to the library.
     * 
     * @param addBookRequest The request model containing details of the book to be added.
     */
    public void postBook(AddBookRequest addBookRequest) {
        Book book = new Book(); // Creating a new Book object
        // Setting book details from the request model
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());

        // Saving the new book to the repository
        bookRepository.save(book);
    }

    /**
     * Deletes a specific book from the library.
     * 
     * @param bookId The ID of the book to be deleted.
     * @throws Exception if the book is not found.
     */
    public void deleteBook(Long bookId) throws Exception {
        // Fetching the book by ID
        Optional<Book> book = bookRepository.findById(bookId);

        // Checking if the book is present
        if (!book.isPresent()) {
            throw new Exception("Book not found"); // Throwing exception if book not found
        }

        // Deleting the book from the repository
        bookRepository.delete(book.get());

        // Deleting related checkouts and reviews
        checkoutRepository.deleteAllByBookId(bookId);
        reviewRepository.deleteAllByBookId(bookId);
    }
}
