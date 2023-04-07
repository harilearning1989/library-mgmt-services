package com.lib.mgmt.services;

import com.lib.mgmt.data.ModelData;
import com.lib.mgmt.exceptions.BookNotFoundException;
import com.lib.mgmt.exceptions.StudentNotFoundException;
import com.lib.mgmt.models.Book;
import com.lib.mgmt.models.ReturnBook;
import com.lib.mgmt.models.Student;
import com.lib.mgmt.repos.library.BookRepository;
import com.lib.mgmt.repos.library.BookReturnRepository;
import com.lib.mgmt.repos.library.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.lib.mgmt.*")
@PowerMockIgnore({"javax.management.*",
        "com.sun.org.apache.xerces.",
        "org.apache.http.conn.ssl.*",
        "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*",
        "javax.xml.", "org.xml.", "org.w3c.dom.","jdk.internal.reflect.*",
        "com.sun.org.apache.xalan.", "javax.activation.*",
        "javax.net.ssl.*","jdk.internal.reflect.*"})
public class BookReturnServiceImplTest {

    @Mock
    private BookReturnRepository bookReturnRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookReturnServiceImpl bookReturnService;

    @Test
    public void allReturnedBooksTest() {
        List<ReturnBook> issueBookList = ModelData.getReturnBookData();
        when(bookReturnRepository.findAll()).thenReturn(issueBookList);

        List<ReturnBook> resultList = bookReturnService.allReturnedBooks();
        assertEquals(resultList,issueBookList);
        assertEquals(resultList.size(),issueBookList.size());
    }

    @Test
    public void returnOldBookTest(){
        Student response = new Student();
        response.setId(12);
        response.setStudentId(76127);
        response.setStudentName("Hari");

        ReturnBook returnBook = new ReturnBook();
        returnBook.setStudentId(76127);
        returnBook.setBookId(12);

        Book book = new Book();
        book.setId(12);

        book.setAvailBooks(4);

        when(studentRepository.findByStudentId(76127)).thenReturn(Optional.of(response));
        when(bookRepository.findById(12)).thenReturn(Optional.of(book));
        ReturnBook returnBookResp = bookReturnService.returnOldBook(returnBook);

    }

    @Test(expected = StudentNotFoundException.class)
    public void returnOldBookStdExceptionTest(){
        Student response = new Student();
        response.setId(12);
        response.setStudentId(76127);
        response.setStudentName("Hari");

        ReturnBook returnBook = new ReturnBook();
        returnBook.setStudentId(76127);
        returnBook.setBookId(12);

        Book book = new Book();
        book.setId(12);

        book.setAvailBooks(4);

        when(studentRepository.findByStudentId(76128)).thenReturn(Optional.of(response));
        when(bookRepository.findById(12)).thenReturn(Optional.of(book));
        ReturnBook returnBookResp = bookReturnService.returnOldBook(returnBook);

    }

    @Test(expected = BookNotFoundException.class)
    public void returnOldBookExceptionTest(){
        Student response = new Student();
        response.setId(12);
        response.setStudentId(76127);
        response.setStudentName("Hari");

        ReturnBook returnBook = new ReturnBook();
        returnBook.setStudentId(76127);
        returnBook.setBookId(12);

        Book book = new Book();
        book.setId(12);

        book.setAvailBooks(4);

        when(studentRepository.findByStudentId(76127)).thenReturn(Optional.of(response));
        when(bookRepository.findById(13)).thenReturn(Optional.of(book));
        ReturnBook returnBookResp = bookReturnService.returnOldBook(returnBook);

    }
}
