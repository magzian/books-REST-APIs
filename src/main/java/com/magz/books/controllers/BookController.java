package com.magz.books.controllers;

import com.magz.books.domain.Book;
import com.magz.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping(path="/books/{isbn}")
    public ResponseEntity<Book> createUpdateBook(
            @PathVariable final String isbn,
            @RequestBody final Book book){

        book.setIsbn(isbn);
        final boolean isBookExists = bookService.isBookExists(book);
        final Book savedBook = bookService.create(book);

        if(isBookExists){
            return new ResponseEntity<Book>(savedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> retrieveBook (@PathVariable final String isbn){
        final Optional<Book> foundBook = bookService.findById(isbn);

        return foundBook.map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> listBooks() {
        return new ResponseEntity<List<Book>>(bookService.listBook(),HttpStatus.OK);
    }



}
