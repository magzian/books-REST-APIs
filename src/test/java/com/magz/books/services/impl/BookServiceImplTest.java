package com.magz.books.services.impl;


import com.magz.books.TestData;
import com.magz.books.domain.Book;
import com.magz.books.domain.BookEntity;
import com.magz.books.repositories.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Tells JUnit to use Mockito for this test class
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository; // Creates a mock implementation of BookRepository
    private BookServiceImpl bookService;

/*    @InjectMocks
    private BookServiceImpl underTest;*/

    @BeforeEach // Runs before each test method

    public void setup() {
        bookService = new BookServiceImpl(bookRepository);   // Injects the mock into our service
    }


   /* The code above so far creates a fresh BookServiceImpl instance before each test, with a mocked BookRepository.*/

    /*The Test Method - Following the AAA (Arrange, Act, Assert) Pattern:*/

    @Test
    public void testCreateBook() {
        // Arrange
        /*Creates a Book object that we'll pass to the service*/
        Book bookToCreate = TestData.testBook();

        /*Creates a BookEntity that will be returned when the repository's save method is called*/

        BookEntity savedEntity = TestData.testBookEntity();

        /*Sets up the mock behavior with when(...).thenReturn(...) - this is crucial for controlling what our dependencies do during the test*/
        when(bookRepository.save(any(BookEntity.class))).thenReturn(savedEntity);

        // Act
        /*This calls the method we're testing.*/
        Book result = bookService.create(bookToCreate);

        // Assert
        /* 1.Verifies that the repository's save method was called exactly once with any BookEntity parameter*/
        /* 2.Checks that the result has the expected values for all three fields*/
        verify(bookRepository).save(any(BookEntity.class));
        assertEquals("978-1234567890", result.getIsbn());
        assertEquals("The Great Novel", result.getTitle());
        assertEquals("Jane Smith", result.getAuthor());
    }

   /* How It All Works Together
    The entire flow of the test is:

    1.We create a Book object representing what a client might pass to our service
    2.We set up a mock repository to return a pre-defined entity when its save method is called
    3.We call the service method being tested
    4.The service converts the book to an entity (using bookToBookEntity)
    5.The service calls the repository's save method with this entity
    6.Our mock returns the pre-defined entity (simulating DB save)
    7.The service converts this entity back to a book object (using bookEntityToBook)
    8.We verify that the repository was called and the returned book has the correct values

    This approach isolates the service layer for testing - we're not testing the repository or database access, just the service's logic. This is the essence of a unit test: testing a single "unit" of code in isolation.
    The key relationships:

    1.BookServiceImpl depends on BookRepository
    2.BookServiceImplTest tests BookServiceImpl by mocking BookRepository
    3.Both Book and BookEntity are used by the service for different purposes

    This separation of concerns makes the code more maintainable and testable.*/


    @Test
    public void testThatFindByIdReturnsEmptyWhenNoBook(){
        final String isbn = "123123123";
        when(bookRepository.findById(eq(isbn))).thenReturn(Optional.empty());

        final Optional<Book> result = bookService.findById(isbn);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testThatFindByIdReturnsBookWhenExists(){
        final Book book = TestData.testBook();
        final BookEntity bookEntity = TestData.testBookEntity();

        when(bookRepository.findById(eq(book.getIsbn()))).thenReturn(Optional.of(bookEntity));

        final Optional<Book> result = bookService.findById(book.getIsbn());
        assertEquals(Optional.of(book), result);
    }

    @Test
    public void testListBookReturnsEmptyListWhenNoBooksExist() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<BookEntity>());
        final List<Book> result = bookService.listBook();
        assertEquals(0, result.size());
    }

    @Test
    public void testListBooksReturnBooksWhenExists() {
        final BookEntity bookEntity = TestData.testBookEntity();

        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        final List<Book> result = bookService.listBook();
        assertEquals(1, result.size());
    }


}


