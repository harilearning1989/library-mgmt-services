package com.lib.mgmt.services;

import com.lib.mgmt.dtos.BooksDTO;
import com.lib.mgmt.models.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book createBook(Book book);

    List<BooksDTO> readJson();

    List<Book> saveAllBooks();

    List<Book> searchBook(Book book);

    List<Book> searchBookCustom(Book book);

    List<Book> findAvailableBooks();
    List<Book> findBookSearchCriteria(String isbn,String subject,String bookName);

    ResponseEntity<Book> updateBook(Book book);

    void deleteByBookId(int bookId);

    long countBooks();
}
