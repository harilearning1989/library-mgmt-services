package com.lib.mgmt.controls;

import com.lib.mgmt.models.library.ReturnBook;
import com.lib.mgmt.services.BookReturnServiceImpl;
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
public class BookReturnControllerTest {

    @InjectMocks
    BookReturnController bookReturnController;

    @Mock
    BookReturnServiceImpl bookReturnService;
    @Test
    public void allReturnedBooksTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<ReturnBook> returnBookData = getReturnBookData();
        when(bookReturnService.allReturnedBooks()).thenReturn(returnBookData);
        ResponseEntity<List<ReturnBook>> responseEntity = bookReturnController.allReturnedBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(returnBookData.size());
    }

    @Test
    public void allReturnedBooksEmptyTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookReturnService.allReturnedBooks()).thenReturn(new ArrayList<>());
        ResponseEntity<List<ReturnBook>> responseEntity = bookReturnController.allReturnedBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void allReturnedBooksExceptionTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookReturnService.allReturnedBooks()).thenReturn(null);
        ResponseEntity<List<ReturnBook>> responseEntity = bookReturnController.allReturnedBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void returnOldBookTest(){
        ReturnBook request = new ReturnBook();
        request.setId(12);

        ReturnBook response = new ReturnBook();
        response.setId(12);

        when(bookReturnService.returnOldBook(request)).thenReturn(response);
        ResponseEntity<ReturnBook> responseEntity = bookReturnController.returnOldBook(request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void returnOldBookExceptionTest(){
        ReturnBook request = new ReturnBook();
        request.setId(12);

        when(bookReturnService.returnOldBook(request)).thenReturn(null);
        ResponseEntity<ReturnBook> responseEntity = bookReturnController.returnOldBook(request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    private List<ReturnBook> getReturnBookData() {
        List<ReturnBook> returnBookList = new ArrayList<>();
        ReturnBook returnBook = new ReturnBook();
        returnBook.setBookId(123);
        returnBook.setStudentId(76127);

        returnBookList.add(returnBook);

        return returnBookList;
    }

}