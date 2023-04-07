package com.lib.mgmt.services;

import com.lib.mgmt.constants.LibraryConstants;
import com.lib.mgmt.exceptions.BookNotFoundException;
import com.lib.mgmt.exceptions.StudentNotFoundException;
import com.lib.mgmt.models.library.Book;
import com.lib.mgmt.models.library.ReturnBook;
import com.lib.mgmt.repos.library.BookRepository;
import com.lib.mgmt.repos.library.BookReturnRepository;
import com.lib.mgmt.repos.library.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BookReturnServiceImpl implements BookReturnService{

    private BookReturnRepository bookReturnRepository;
    private StudentRepository studentRepository;
    private BookRepository bookRepository;
    @Autowired
    public void setBookReturnRepository(BookReturnRepository bookReturnRepository) {
        this.bookReturnRepository = bookReturnRepository;
    }
    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Override
    public List<ReturnBook> allReturnedBooks() {
        return bookReturnRepository.findAll();
    }

    @Override
    @Transactional
    public ReturnBook returnOldBook(ReturnBook returnBook) {
        studentRepository.findByStudentId(returnBook.getStudentId()).orElseThrow(
                ()-> new StudentNotFoundException(LibraryConstants.NO_STUDENT_FOUND_WITH_THIS_ID + returnBook.getStudentId()));
        Book book = bookRepository.findById(returnBook.getBookId()).orElseThrow(
                ()-> new BookNotFoundException(LibraryConstants.NO_BOOK_FOUND_WITH_THIS_ID + returnBook.getBookId()));
        int availableBooks = book.getAvailBooks();
        bookRepository.updateAvailableBooks(book.getId(),availableBooks+1);
        returnBook.setReturnDate(new Date());
        return bookReturnRepository.save(returnBook);
    }
}
