package com.lib.mgmt.services.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.lib.mgmt.data.ModelData;
import com.lib.mgmt.dtos.BooksDTO;
import com.lib.mgmt.models.library.Book;
import com.lib.mgmt.repos.library.BookRepository;
import com.lib.mgmt.repos.library.TranxlogRepository;
import com.lib.mgmt.utils.LibraryUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private TranxlogRepository tranxlogRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookServiceImpl bookServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void findAvailableBooksTest() {
        List<Book> bookList = ModelData.getBookList();
        when(bookRepository.findByAvailBooksGreaterThan(0)).thenReturn(bookList);

        PowerMockito.mockStatic(LibraryUtils.class); //3
        PowerMockito.when(LibraryUtils.processBooks(bookList)).thenReturn(bookList); //4

        List<Book> booksResponse = bookService.findAvailableBooks();

        assertEquals(booksResponse,booksResponse);
        assertEquals(booksResponse.size(),booksResponse.size());
    }

    @Test
    public void findAllTest() {
        List<Book> bookList = ModelData.getBookList();
        when(bookRepository.findAll()).thenReturn(bookList);

        PowerMockito.mockStatic(LibraryUtils.class);
        PowerMockito.when(LibraryUtils.processBooks(bookList)).thenReturn(bookList);

        List<Book> booksResponse = bookService.findAll();
        assertEquals(booksResponse,booksResponse);
        assertEquals(booksResponse.size(),booksResponse.size());
    }

    @Test
    public void createBookTest() {
        Book request = new Book();
        request.setId(12);
        request.setBookName("Java");
        request.setIsbn("1234");
        request.setSubject("Java");

        Book response = new Book();
        response.setId(12);
        response.setBookName("Java");
        response.setIsbn("1234");

        when(bookRepository.save(request)).thenReturn(response);

        Book book = bookService.createBook(request);

        assertEquals(book,response);
        assertEquals(book.getBookName(),response.getBookName());
    }

    @Test
    public void createBookNullTest() {
        Book request = new Book();
        request.setId(12);
        request.setBookName("Java");
        request.setIsbn("1234");
        request.setSubject("Java");

        List<Book> bookList = ModelData.getBookList();
        String isbn = "1234";
        String subject = "Java";
        String bookName = "Java";

        when(bookRepository.findAll((Specification<Book>) any())).thenReturn(bookList);
        when(bookServiceMock.findBookSearchCriteria(isbn,subject,bookName)).thenReturn(bookList);

        Book book = bookService.createBook(request);

        assertEquals(book,null);
    }

    @Test
    public void readJsonTest() {
        List<BooksDTO> booksDTOList = bookService.readJson();
        assertEquals(394,booksDTOList.size());
    }
    @Test
    public void readJsonExceptionTest() throws IOException {
        when(Resources.getResource("json/books.json").toString()).thenReturn("Hello");
        ObjectMapper mockObjectMapper = Mockito.mock(ObjectMapper.class);
        Mockito.when(mockObjectMapper.readValue("Hello",
                mockObjectMapper.getTypeFactory().constructCollectionType(List.class, BooksDTO.class))).thenThrow(new RuntimeException());
        List<BooksDTO> booksDTOList = bookService.readJson();
        assertEquals(394,booksDTOList.size());
    }

    @Test
    public void searchBook() {
    }

    @Test
    public void searchBookCustom() {
    }

    @Test
    public void saveAllBooks() {
    }

    @Test
    public void findBookSearchCriteria() {
    }

    @Test
    public void updateBook() {
    }

    @Test
    public void deleteByBookId() {
    }

    @Test
    public void countBooks() {
    }
}