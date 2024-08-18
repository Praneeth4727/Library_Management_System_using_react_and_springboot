package com.bibliotheca.spring_boot_library.service;

import java.text.SimpleDateFormat; // Importing for date formatting
import java.time.LocalDate; // Importing for handling dates
import java.util.ArrayList; // Importing for using ArrayList
import java.util.Date; // Importing for handling dates
import java.util.List; // Importing for using List
import java.util.Optional; // Importing for handling optional values
import java.util.concurrent.TimeUnit; // Importing for time unit conversions

import com.bibliotheca.spring_boot_library.dao.BookRepository; // Importing BookRepository for managing books
import com.bibliotheca.spring_boot_library.dao.CheckoutRepository; // Importing CheckoutRepository for managing checkouts
import com.bibliotheca.spring_boot_library.dao.HistoryRepository; // Importing HistoryRepository for managing history records
import com.bibliotheca.spring_boot_library.dao.PaymentRepository; // Importing PaymentRepository for managing payments
import com.bibliotheca.spring_boot_library.entity.Book; // Importing Book entity
import com.bibliotheca.spring_boot_library.entity.Checkout; // Importing Checkout entity
import com.bibliotheca.spring_boot_library.entity.History; // Importing History entity
import com.bibliotheca.spring_boot_library.entity.Payment; // Importing Payment entity
import com.bibliotheca.spring_boot_library.responsemodels.ShelfCurrentLoansResponse; // Importing response model for current loans

import org.springframework.stereotype.Service; // Importing Service to define a service class
import org.springframework.transaction.annotation.Transactional; // Importing Transactional for managing transactions

@Service // Marking this class as a service component
@Transactional // Enabling transaction management for the class
public class BookService {
    // Repositories for database operations
    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;
    private HistoryRepository historyRepository;
    private PaymentRepository paymentRepository;

    // Constructor-based dependency injection
    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository,
                       HistoryRepository historyRepository, PaymentRepository paymentRepository) {
        this.bookRepository = bookRepository; // Initializing bookRepository
        this.checkoutRepository = checkoutRepository; // Initializing checkoutRepository
        this.historyRepository = historyRepository; // Initializing historyRepository
        this.paymentRepository = paymentRepository; // Initializing paymentRepository
    }

    /**
     * Checks out a book for a user.
     * 
     * @param userEmail The email of the user checking out the book.
     * @param bookId The ID of the book to be checked out.
     * @throws Exception if the book doesn't exist, is already checked out, or if there are outstanding fees.
     */
    public Book checkoutBook(String userEmail, Long bookId) throws Exception {
        // Fetching the book by ID
        Optional<Book> book = bookRepository.findById(bookId);

        // Validating the checkout conditions
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out by user");
        }

        // Fetching currently checked out books for the user
        List<Checkout> currentBooksCheckedOut = checkoutRepository.findBooksByUserEmail(userEmail);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        boolean bookNeedsReturned = false; // Flag to check if any book is overdue

        // Checking if any book needs to be returned
        for (Checkout checkout : currentBooksCheckedOut) {
            Date d1 = sdf.parse(checkout.getReturnDate());
            Date d2 = sdf.parse(LocalDate.now().toString());
            TimeUnit time = TimeUnit.DAYS;

            double differenceInTime = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);
            if (differenceInTime < 0) {
                bookNeedsReturned = true; // Set flag if a book is overdue
                break;
            }
        }

        // Checking for outstanding fees
        Payment userPayment = paymentRepository.findByUserEmail(userEmail);
        if ((userPayment != null && userPayment.getAmount() > 0) || (userPayment != null && bookNeedsReturned)) {
            throw new Exception("Outstanding fees"); // Throwing exception if there are fees
        }

        // If userPayment is null, create a new payment record with zero amount
        if (userPayment == null) {
            Payment payment = new Payment();
            payment.setAmount(00.00);
            payment.setUserEmail(userEmail);
            paymentRepository.save(payment);
        }

        // Updating the book's available copies
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        // Creating a new checkout record for the book
        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()
        );
        checkoutRepository.save(checkout);

        return book.get(); // Returning the checked-out book
    }

    /**
     * Checks if a book is checked out by a user.
     * 
     * @param userEmail The email of the user.
     * @param bookId The ID of the book.
     * @return True if the book is checked out by the user, otherwise false.
     */
    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        return validateCheckout != null; // Returns true if the user has checked out the book
    }

    /**
     * Counts the number of books currently loaned to a user.
     * 
     * @param userEmail The email of the user.
     * @return The number of current loans for the user.
     */
    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size(); // Returns the size of the checked-out books list
    }

    /**
     * Retrieves the current loans for a user.
     * 
     * @param userEmail The email of the user.
     * @return A list of current loans with details.
     * @throws Exception if an error occurs while processing.
     */
    public List<ShelfCurrentLoansResponse> currentLoans(String userEmail) throws Exception {
        List<ShelfCurrentLoansResponse> shelfCurrentLoansResponses = new ArrayList<>(); // List to hold current loans responses

        // Fetching the checkout records for the user
        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
        List<Long> bookIdList = new ArrayList<>(); // List to hold book IDs

        // Collecting book IDs from the checkout records
        for (Checkout i : checkoutList) {
            bookIdList.add(i.getBookId());
        }

        // Fetching books based on collected book IDs
        List<Book> books = bookRepository.findBooksByBookIds(bookIdList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Creating response objects for current loans
        for (Book book : books) {
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(x -> x.getBookId() == book.getId()).findFirst();

            if (checkout.isPresent()) {
                Date d1 = sdf.parse(checkout.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());
                TimeUnit time = TimeUnit.DAYS;

                long difference_In_Time = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);
                shelfCurrentLoansResponses.add(new ShelfCurrentLoansResponse(book, (int) difference_In_Time)); // Adding response object to the list
            }
        }
        return shelfCurrentLoansResponses; // Returning the list of current loans
    }

    /**
     * Returns a book checked out by a user.
     * 
     * @param userEmail The email of the user.
     * @param bookId The ID of the book to be returned.
     * @throws Exception if the book doesn't exist or is not checked out by the user.
     */
    public void returnBook(String userEmail, Long bookId) throws Exception {
        // Fetching the book by ID
        Optional<Book> book = bookRepository.findById(bookId);

        // Validating if the book is checked out by the user
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (!book.isPresent() || validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        // Updating the book's available copies
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
        bookRepository.save(book.get());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Calculating the difference between return date and current date
        Date d1 = sdf.parse(validateCheckout.getReturnDate());
        Date d2 = sdf.parse(LocalDate.now().toString());
        TimeUnit time = TimeUnit.DAYS;

        double differenceInTime = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

        // If the book is returned late, update the payment amount
        if (differenceInTime < 0) {
            Payment payment = paymentRepository.findByUserEmail(userEmail);
            payment.setAmount(payment.getAmount() + (differenceInTime * -1)); // Charge for overdue days
            paymentRepository.save(payment);
        }

        // Deleting the checkout record for the book
        checkoutRepository.deleteById(validateCheckout.getId());

        // Creating a history record for the returned book
        History history = new History(
                userEmail,
                validateCheckout.getCheckoutDate(),
                LocalDate.now().toString(),
                book.get().getTitle(),
                book.get().getAuthor(),
                book.get().getDescription(),
                book.get().getImg()
        );
        historyRepository.save(history); // Saving the history record
    }

    /**
     * Renews the loan for a book checked out by a user.
     * 
     * @param userEmail The email of the user.
     * @param bookId The ID of the book to be renewed.
     * @throws Exception if the book is not checked out by the user.
     */
    public void renewLoan(String userEmail, Long bookId) throws Exception {
        // Validating if the book is checked out by the user
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        if (validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Checking the return date of the checked-out book
        Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
        Date d2 = sdFormat.parse(LocalDate.now().toString());

        // If the book is not overdue, extend the return date by 7 days
        if (d1.compareTo(d2) >= 0) {
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkoutRepository.save(validateCheckout); // Saving the updated checkout record
        }
    }
}
