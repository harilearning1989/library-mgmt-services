package com.lib.mgmt.services.library;

import com.lib.mgmt.dtos.BooksDTO;
import com.lib.mgmt.models.library.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book createBook(Book book);

    List<BooksDTO> readJson();

    List<Book> saveAllBooks();

    List<Book> searchBook(Book book);

    List<Book> searchBookCustom(Book book);

    List<Book> findAvailableBooks();
    List<Book> findBookSearchCriteria(int isbn,String subject,String bookName);

    Book updateBook(Book book);

    void deleteByBookId(int bookId);

    long countBooks();
}
