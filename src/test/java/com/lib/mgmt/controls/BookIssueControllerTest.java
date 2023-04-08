package com.lib.mgmt.controls;

import com.lib.mgmt.data.ModelData;
import com.lib.mgmt.dtos.IssueBookDto;
import com.lib.mgmt.dtos.IssuedBookStudentDto;
import com.lib.mgmt.models.library.IssueBook;
import com.lib.mgmt.services.library.BookIssueServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
public class BookIssueControllerTest {

    @InjectMocks
    BookIssueController bookIssueController;

    @Mock
    BookIssueServiceImpl bookIssueService;

    @Test
    public void findAllIssuedBooksTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<IssueBook> issueBookList = ModelData.getIssueBookList();
        when(bookIssueService.findAllIssuedBooks()).thenReturn(issueBookList);
        ResponseEntity<List<IssueBook>> responseEntity = bookIssueController.findAllIssuedBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(issueBookList.size());
    }

    @Test
    public void findAllIssuedBooksEmptyTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookIssueService.findAllIssuedBooks()).thenReturn(new ArrayList<>());
        ResponseEntity<List<IssueBook>> responseEntity = bookIssueController.findAllIssuedBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void findAllIssuedBooksExceptionTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookIssueService.findAllIssuedBooks()).thenReturn(null);
        ResponseEntity<List<IssueBook>> responseEntity = bookIssueController.findAllIssuedBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void issueNewBookTest() {
        IssueBookDto request = new IssueBookDto();
        request.setBookId(1234);
        request.setStudentId(76127);

        IssueBook response = new IssueBook();
        response.setBookId(1234);
        response.setId(12);
        response.setStudentId(76127);

        when(bookIssueService.issueNewBook(request)).thenReturn(response);
        ResponseEntity<IssueBook> responseEntity = bookIssueController.issueNewBook(request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void issueNewBookExceptionTest() {
        IssueBookDto request = new IssueBookDto();
        request.setBookId(1234);
        request.setStudentId(76127);

        IssueBook response = new IssueBook();
        response.setBookId(1234);
        response.setId(12);
        response.setStudentId(76127);

        when(bookIssueService.issueNewBook(request)).thenReturn(null);
        ResponseEntity<IssueBook> responseEntity = bookIssueController.issueNewBook(request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void findIssuedBooksForStudentTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<IssuedBookStudentDto> bookList = getIssueBookDtoList();
        when(bookIssueService.findIssuedBooksForStudent(76127)).thenReturn(bookList);
        ResponseEntity<List<IssuedBookStudentDto>> responseEntity = bookIssueController.findIssuedBooksForStudent(76127);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(bookList.size());
    }
    @Test
    public void findIssuedBooksForStudentEmptyTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookIssueService.findIssuedBooksForStudent(76127)).thenReturn(new ArrayList<>());
        ResponseEntity<List<IssuedBookStudentDto>> responseEntity = bookIssueController.findIssuedBooksForStudent(76127);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void findIssuedBooksForStudentExceptionTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookIssueService.findIssuedBooksForStudent(76127)).thenReturn(null);
        ResponseEntity<List<IssuedBookStudentDto>> responseEntity = bookIssueController.findIssuedBooksForStudent(76127);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void sameBookIssuedForStudentsTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<IssuedBookStudentDto> bookList = getIssueBookDtoList();
        when(bookIssueService.sameBookIssuedForStudents(76127)).thenReturn(bookList);
        ResponseEntity<List<IssuedBookStudentDto>> responseEntity = bookIssueController.sameBookIssuedForStudents(76127);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(bookList.size());
    }
    @Test
    public void sameBookIssuedForStudentsEmptyTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookIssueService.sameBookIssuedForStudents(76127)).thenReturn(new ArrayList<>());
        ResponseEntity<List<IssuedBookStudentDto>> responseEntity = bookIssueController.sameBookIssuedForStudents(76127);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void sameBookIssuedForStudentsExceptionTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookIssueService.sameBookIssuedForStudents(76127)).thenReturn(null);
        ResponseEntity<List<IssuedBookStudentDto>> responseEntity = bookIssueController.sameBookIssuedForStudents(76127);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    private List<IssuedBookStudentDto> getIssueBookDtoList() {
        List<IssuedBookStudentDto> bookList = new ArrayList<>();

        IssuedBookStudentDto dto = new IssuedBookStudentDto();
        dto.setBookId(1234);
        dto.setBookName("Java");

        bookList.add(dto);
        dto = new IssuedBookStudentDto();
        dto.setBookId(1234);
        dto.setBookName("Java");

        bookList.add(dto);
        return bookList;
    }
}