package com.bibliotheca.spring_boot_library.dao; // Package declaration for the Data Access Object (DAO) layer

import java.util.List; // Importing List for handling collections of books

import com.bibliotheca.spring_boot_library.entity.Book; // Importing the Book entity

import org.springframework.data.domain.Page; // Importing Page for pagination support
import org.springframework.data.domain.Pageable; // Importing Pageable for page request details
import org.springframework.data.jpa.repository.JpaRepository; // Importing JpaRepository for basic CRUD operations
import org.springframework.data.jpa.repository.Query; // Importing Query for custom JPQL queries
import org.springframework.data.repository.query.Param; // Importing Param for named parameters in queries
import org.springframework.web.bind.annotation.RequestParam; // Importing RequestParam for handling request parameters in controllers

/**
 * Repository interface for performing CRUD operations on Book entities.
 * This interface extends JpaRepository, which provides a set of standard database operations.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Finds books whose titles contain the specified string.
     *
     * @param title   the string to search for in book titles
     * @param pageable pagination information
     * @return a page of books with titles containing the specified string
     */
    Page<Book> findByTitleContaining(@RequestParam("title") String title, Pageable pageable);
    
    /**
     * Finds books belonging to a specified category.
     *
     * @param category the category to filter books by
     * @param pageable pagination information
     * @return a page of books in the specified category
     */
    Page<Book> findByCategory(@RequestParam("category") String category, Pageable pageable);
    
    /**
     * Finds books based on a list of book IDs.
     *
     * @param bookId a list of book IDs to search for
     * @return a list of books that match the specified IDs
     */
    @Query("select o from Book o where id in :book_ids") // Custom JPQL query to select books by their IDs
    List<Book> findBooksByBookIds(@Param("book_ids") List<Long> bookId); // Method to retrieve books based on a list of IDs
}
