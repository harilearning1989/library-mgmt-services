package com.lib.mgmt.services;

import com.lib.mgmt.data.ModelData;
import com.lib.mgmt.dtos.IssueBookDto;
import com.lib.mgmt.dtos.IssuedBookStudentDto;
import com.lib.mgmt.exceptions.BookAlreadyIssuedException;
import com.lib.mgmt.exceptions.BookNotFoundException;
import com.lib.mgmt.exceptions.StudentNotFoundException;
import com.lib.mgmt.models.Book;
import com.lib.mgmt.models.IssueBook;
import com.lib.mgmt.models.Student;
import com.lib.mgmt.repos.BookIssueRepository;
import com.lib.mgmt.repos.BookRepository;
import com.lib.mgmt.repos.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.lib.mgmt.*")
@PowerMockIgnore({"javax.management.*",
        "com.sun.org.apache.xerces.",
        "org.apache.http.conn.ssl.*",
        "com.amazonaws.http.conn.ssl.*", "javax.net.ssl.*",
        "javax.xml.", "org.xml.", "org.w3c.dom.","jdk.internal.reflect.*",
        "com.sun.org.apache.xalan.", "javax.activation.*",
        "javax.net.ssl.*","jdk.internal.reflect.*"})
public class BookIssueServiceImplTest {

    @Mock
    private BookIssueRepository bookIssueRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookIssueServiceImpl bookIssueService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllTest() {
        List<IssueBook> issueBookList = ModelData.getIssueBookList();
        when(bookIssueRepository.findAll()).thenReturn(issueBookList);

        List<IssueBook> resultList = bookIssueService.findAllIssuedBooks();
        assertEquals(resultList,issueBookList);
        assertEquals(resultList.size(),issueBookList.size());
    }

    @Test
    public void issueNewBookTest(){
        Student response = new Student();
        response.setId(12);
        response.setStudentId(76127);
        response.setStudentName("Hari");
        Book book = new Book();
        book.setId(12);
        book.setAvailBooks(4);

        IssueBook requet = new IssueBook();
        requet.setStudentId(76127);
        IssueBook responseIssueBook = new IssueBook();
        responseIssueBook.setStudentId(76127);

        when(studentRepository.findByStudentId(76127)).thenReturn(Optional.of(response));
        when(bookRepository.findByIdAndAvailBooksGreaterThanEqual(12,1)).thenReturn(Optional.of(book));
        when(bookIssueRepository.countByBookIdAndStudentId(12,76127)).thenReturn(2l);
        when(bookIssueRepository.save(any(IssueBook.class))).thenReturn(responseIssueBook);

        IssueBookDto dto = new IssueBookDto();
        dto.setStudentId(76127);
        dto.setBookId(12);
        IssueBook issueBook = bookIssueService.issueNewBook(dto);

        assertEquals(response.getStudentId(),issueBook.getStudentId());
    }
    @Test(expected = StudentNotFoundException.class)
    public void issueNewBookExceptionTest(){
        Student response = new Student();
        response.setId(12);
        response.setStudentId(76127);
        response.setStudentName("Hari");
        when(studentRepository.findByStudentId(76127)).thenReturn(Optional.of(response));

        IssueBookDto dto = new IssueBookDto();
        dto.setStudentId(76128);
        IssueBook issueBook = bookIssueService.issueNewBook(dto);

        assertEquals(response.getStudentId(),issueBook.getStudentId());
    }

    @Test(expected = BookNotFoundException.class)
    public void issueNewBookNotFoundTest(){
        Student response = new Student();
        response.setId(12);
        response.setStudentId(76127);
        response.setStudentName("Hari");

        Book book = new Book();
        book.setId(22);
        book.setAvailBooks(4);

        when(studentRepository.findByStudentId(76127)).thenReturn(Optional.of(response));
        when(bookRepository.findByIdAndAvailBooksGreaterThanEqual(12,1)).thenReturn(Optional.of(book));

        IssueBookDto dto = new IssueBookDto();
        dto.setStudentId(76127);
        IssueBook issueBook = bookIssueService.issueNewBook(dto);

        assertEquals(response.getStudentId(),issueBook.getStudentId());
    }

    @Test(expected = BookAlreadyIssuedException.class)
    public void issueNewBookCountTest(){
        Student response = new Student();
        response.setId(12);
        response.setStudentId(76127);
        response.setStudentName("Hari");
        Book book = new Book();
        book.setId(12);
        book.setAvailBooks(4);

        IssueBook requet = new IssueBook();
        requet.setStudentId(76127);
        IssueBook responseIssueBook = new IssueBook();
        responseIssueBook.setStudentId(76127);

        when(studentRepository.findByStudentId(76127)).thenReturn(Optional.of(response));
        when(bookRepository.findByIdAndAvailBooksGreaterThanEqual(12,1)).thenReturn(Optional.of(book));
        when(bookIssueRepository.countByBookIdAndStudentId(12,76127)).thenReturn(7l);
        when(bookIssueRepository.save(any(IssueBook.class))).thenReturn(responseIssueBook);

        IssueBookDto dto = new IssueBookDto();
        dto.setStudentId(76127);
        dto.setBookId(12);
        IssueBook issueBook = bookIssueService.issueNewBook(dto);

        assertEquals(response.getStudentId(),issueBook.getStudentId());
    }

    @Test
    public void findIssuedBooksForStudentTest(){
        List<IssuedBookStudentDto> issuedBookStudentDtos = ModelData.getIssuedBookStudentDtos();
        when(bookIssueRepository.findIssuedBooksForStudent(76127)).thenReturn(issuedBookStudentDtos);

        List<IssuedBookStudentDto> issuedBookStudentDtoList = bookIssueService.findIssuedBooksForStudent(76127);

        assertEquals(issuedBookStudentDtos,issuedBookStudentDtoList);
        assertEquals(issuedBookStudentDtos.size(),issuedBookStudentDtoList.size());
    }
    @Test
    public void sameBookIssuedForStudentsTest(){
        List<IssuedBookStudentDto> issuedBookStudentDtos = ModelData.getIssuedBookStudentDtos();
        when(bookIssueRepository.sameBookIssuedForStudents(12)).thenReturn(issuedBookStudentDtos);

        List<IssuedBookStudentDto> issuedBookStudentDtoList = bookIssueService.sameBookIssuedForStudents(12);

        assertEquals(issuedBookStudentDtos,issuedBookStudentDtoList);
        assertEquals(issuedBookStudentDtos.size(),issuedBookStudentDtoList.size());
    }
}
