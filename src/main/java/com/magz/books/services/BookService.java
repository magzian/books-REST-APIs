package com.magz.books.services;

import com.magz.books.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    boolean isBookExists(Book book);

    Book create(Book book);

    Optional<Book> findById(String isbn);

    List<Book> listBook();

    void deleteBookById(String isbn);
}
