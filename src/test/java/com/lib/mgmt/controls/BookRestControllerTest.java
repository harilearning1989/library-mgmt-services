package com.lib.mgmt.controls;

import com.lib.mgmt.data.ModelData;
import com.lib.mgmt.dtos.BooksDTO;
import com.lib.mgmt.models.Book;
import com.lib.mgmt.services.BookServiceImpl;
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
public class BookRestControllerTest {

    @InjectMocks
    BookRestController bookRestController;

    @Mock
    BookServiceImpl bookService;

    @Test
    public void findAllNotEmptyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = ModelData.getBookList();
        when(bookService.findAll()).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.findAllBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(bookList.size());
    }

    @Test
    public void findAllEmptyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = new ArrayList<>();
        when(bookService.findAll()).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.findAllBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void findAllExceptionTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookService.findAll()).thenReturn(null);
        ResponseEntity<List<Book>> responseEntity = bookRestController.findAllBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void searchBookCustomNotEmptyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = ModelData.getBookList();
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        when(bookService.searchBookCustom(book)).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.searchBookCustom(book);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(bookList.size());
    }

    @Test
    public void searchBookCustomEmptyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = new ArrayList<>();
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        when(bookService.searchBookCustom(book)).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.searchBookCustom(book);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void searchBookCustomExceptionTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        when(bookService.searchBookCustom(book)).thenReturn(null);
        ResponseEntity<List<Book>> responseEntity = bookRestController.searchBookCustom(book);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void createBookTest() {
        Book request = new Book();
        request.setId(12);
        request.setBookName("Java");
        request.setIsbn("1234");

        Book response = new Book();
        response.setId(12);
        response.setBookName("Java");
        response.setIsbn("1234");

        when(bookService.createBook(request)).thenReturn(response);
        ResponseEntity<Book> responseEntity = bookRestController.createBook(request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void createBookExceptionTest() {
        Book request = new Book();
        request.setId(12);
        request.setBookName("Java");
        request.setIsbn("1234");

        when(bookService.createBook(request)).thenReturn(null);
        ResponseEntity<Book> responseEntity = bookRestController.createBook(request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void findAvailableBooksTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = ModelData.getBookList();
        when(bookService.findAvailableBooks()).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.findAvailableBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(bookList.size());
    }

    @Test
    public void findAvailableBooksEmptyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = new ArrayList<>();
        when(bookService.findAvailableBooks()).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.findAvailableBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void findAvailableBooksExceptionTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookService.findAvailableBooks()).thenReturn(null);
        ResponseEntity<List<Book>> responseEntity = bookRestController.findAvailableBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void saveAllBooksTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = ModelData.getBookList();
        when(bookService.saveAllBooks()).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.saveAllBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(bookList.size());
    }

    @Test
    public void saveAllBooksEmptyTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = new ArrayList<>();
        when(bookService.saveAllBooks()).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.saveAllBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void saveAllBooksExceptionTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookService.saveAllBooks()).thenReturn(null);
        ResponseEntity<List<Book>> responseEntity = bookRestController.saveAllBooks();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void readJsonTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<BooksDTO> booksDTOList = getBookDtoList();
        when(bookService.readJson()).thenReturn(booksDTOList);
        ResponseEntity<List<BooksDTO>> responseEntity = bookRestController.readJson();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(booksDTOList.size());
    }

    @Test
    public void readJsonEmptyTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<BooksDTO> booksDTOList = new ArrayList<>();
        when(bookService.readJson()).thenReturn(booksDTOList);
        ResponseEntity<List<BooksDTO>> responseEntity = bookRestController.readJson();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void readJsonExceptionTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookService.readJson()).thenReturn(null);
        ResponseEntity<List<BooksDTO>> responseEntity = bookRestController.readJson();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void searchBookOldTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = ModelData.getBookList();
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        when(bookService.searchBook(book)).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.searchBookOld(book);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(bookList.size());
    }

    @Test
    public void searchBookOldEmptyTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = new ArrayList<>();
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        when(bookService.searchBook(book)).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.searchBookOld(book);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void searchBookOldExceptionTest(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");

        when(bookService.searchBook(book)).thenReturn(null);
        ResponseEntity<List<Book>> responseEntity = bookRestController.searchBookOld(book);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void findBookSearchCriteriaTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = ModelData.getBookList();
        when(bookService.findBookSearchCriteria("1617290904","Java","Mc Tata")).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.findBookSearchCriteria("1617290904","Java","Mc Tata");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(bookList.size());
    }

    @Test
    public void findBookSearchCriteriaEmptyTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        List<Book> bookList = new ArrayList<>();
        when(bookService.findBookSearchCriteria("1617290904","Java","Mc Tata")).thenReturn(bookList);
        ResponseEntity<List<Book>> responseEntity = bookRestController.findBookSearchCriteria("1617290904","Java","Mc Tata");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }@Test
    public void findBookSearchCriteriaExceptionTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(bookService.findBookSearchCriteria("1617290904","Java","Mc Tata")).thenReturn(null);
        ResponseEntity<List<Book>> responseEntity = bookRestController.findBookSearchCriteria("1617290904","Java","Mc Tata");
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
    @Test
    public void updateBookTest() {
        Book request = new Book();
        request.setId(12);
        request.setBookName("Java");
        request.setIsbn("1234");

        Book response = new Book();
        response.setId(12);
        response.setBookName("Java");
        response.setIsbn("1234");

        when(bookService.updateBook(request)).thenReturn(response);
        ResponseEntity<Book> responseEntity = bookRestController.updateBook(request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    public void updateBookExceptionTest() {
        Book request = new Book();
        request.setId(12);
        request.setBookName("Java");
        request.setIsbn("1234");

        when(bookService.updateBook(request)).thenReturn(null);
        ResponseEntity<Book> responseEntity = bookRestController.updateBook(request);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }

    @Test
    public void deleteBookTest() {
        doNothing().when(bookService).deleteByBookId(10);
        ResponseEntity<String> responseEntity = bookRestController.deleteBook(10);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteBookExceptionTest() {
        doThrow(new NullPointerException()).when(bookService).deleteByBookId(10);
        ResponseEntity<String> responseEntity = bookRestController.deleteBook(10);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(500);
    }
    private List<BooksDTO> getBookDtoList() {
        List<BooksDTO> booksDTOList = new ArrayList<>();

        BooksDTO bookDto = new BooksDTO();
        bookDto.setIsbn("1234");
        booksDTOList.add(bookDto);

        bookDto = new BooksDTO();
        bookDto.setIsbn("1234");
        booksDTOList.add(bookDto);

        return booksDTOList;
    }

}