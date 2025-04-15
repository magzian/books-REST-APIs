package com.magz.books;

import com.magz.books.domain.Book;
import com.magz.books.domain.BookEntity;

public class TestData {
    private TestData(){
    }

    public static Book testBook(){
        Book book = new Book();
        book.setIsbn("978-1234567890");
        book.setTitle("The Great Novel");
        book.setAuthor("Jane Smith");
        return book;
    }

    public static BookEntity testBookEntity(){
        BookEntity bookEntity = new BookEntity();
        bookEntity.setIsbn("978-1234567890");
        bookEntity.setTitle("The Great Novel");
        bookEntity.setAuthor("Jane Smith");
        return bookEntity;
    }
}
