package com.lib.mgmt.controls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.mgmt.dtos.BooksDTO;
import com.lib.mgmt.models.library.Book;
import com.lib.mgmt.services.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
public class BookRestControllerMvcTest {

    @MockBean
    private BookServiceImpl bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findAllBooksTest()throws Exception {
        List<Book> bookList = getBookList();
        when(bookService.findAll()).thenReturn(bookList);
        mockMvc.perform(get("/book/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(bookList.size()))
                .andDo(print());
    }
    @Test
    public void findAllBooksEmptyTest()throws Exception {
        when(bookService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/book/all"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
    @Test
    public void findAllBooksExceptionTest()throws Exception {
        when(bookService.findAll()).thenReturn(null);
        mockMvc.perform(get("/book/all"))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void searchBookCustomTest()throws Exception {
        List<Book> bookList = getBookList();

        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        given(bookService.searchBookCustom(any(Book.class)))
                .willReturn(bookList);

        ResultActions response = mockMvc.perform(post("/book/searchBookCustom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(bookList.size()))
                .andDo(print());
    }

    @Test
    public void searchBookCustomEmptyTest()throws Exception {
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        given(bookService.searchBookCustom(any(Book.class)))
                .willReturn(new ArrayList<>());

        ResultActions response = mockMvc.perform(post("/book/searchBookCustom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andDo(print()).
                andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void searchBookCustomExceptionTest()throws Exception {
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        given(bookService.searchBookCustom(any(Book.class)))
                .willReturn(null);

        ResultActions response = mockMvc.perform(post("/book/searchBookCustom")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void createBookTest() throws Exception {
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        given(bookService.createBook(any(Book.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookName").value(book.getBookName()));
    }

    @Test
    public void createBookExceptionTest()throws Exception {
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        given(bookService.createBook(any(Book.class)))
                .willReturn(null);

        ResultActions response = mockMvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void findAvailableBooksTest() throws Exception {
        List<Book> bookList = getBookList();
        when(bookService.findAvailableBooks()).thenReturn(bookList);
        mockMvc.perform(get("/book/availableBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(bookList.size()))
                .andDo(print());
    }

    @Test
    public void findAvailableBooksEmptyTest() throws Exception {
        when(bookService.findAvailableBooks()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/book/availableBooks"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void findAvailableBooksExceptionTest() throws Exception {
        when(bookService.findAvailableBooks()).thenReturn(null);
        mockMvc.perform(get("/book/availableBooks"))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void saveAllBooksTest() throws Exception {
        List<Book> bookList = getBookList();
        when(bookService.saveAllBooks()).thenReturn(bookList);
        mockMvc.perform(get("/book/saveAllBooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(bookList.size()))
                .andDo(print());
    }

    @Test
    public void saveAllBooksEmptyTest() throws Exception {
        when(bookService.saveAllBooks()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/book/saveAllBooks"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void saveAllBooksExceptionTest() throws Exception {
        when(bookService.saveAllBooks()).thenReturn(null);
        mockMvc.perform(get("/book/saveAllBooks"))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void readJsonTest() throws Exception {
        List<BooksDTO> booksDTOList = getBookDtoList();
        when(bookService.readJson()).thenReturn(booksDTOList);
        mockMvc.perform(get("/book/readJson"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(booksDTOList.size()))
                .andDo(print());
    }

    @Test
    public void readJsonEmptyTest() throws Exception {
        when(bookService.readJson()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/book/readJson"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void readJsonExceptionTest() throws Exception {
        when(bookService.readJson()).thenReturn(null);
        mockMvc.perform(get("/book/readJson"))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void searchBookOldTest()throws Exception {
        List<Book> bookList = getBookList();

        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        given(bookService.searchBook(any(Book.class)))
                .willReturn(bookList);

        ResultActions response = mockMvc.perform(post("/book/searchBookOld")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(bookList.size()))
                .andDo(print());
    }

    @Test
    public void searchBookOldEmptyTest()throws Exception {
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        given(bookService.searchBook(any(Book.class)))
                .willReturn(new ArrayList<>());

        ResultActions response = mockMvc.perform(post("/book/searchBookOld")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andDo(print()).
                andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void searchBookOldExceptionTest()throws Exception {
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        given(bookService.searchBook(any(Book.class)))
                .willReturn(null);

        ResultActions response = mockMvc.perform(post("/book/searchBookOld")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void findBookSearchCriteriaTest() throws Exception {
        List<Book> bookList = getBookList();
        when(bookService.findBookSearchCriteria(anyString(),anyString(),anyString())).thenReturn(bookList);
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("isbn", anyString());
        paramsMap.add("subject", anyString());
        paramsMap.add("bookName", anyString());
        mockMvc.perform(get("/book/searchBook").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(bookList.size()))
                .andDo(print());
    }

    @Test
    public void findBookSearchCriteriaEmptyTest() throws Exception {
        when(bookService.findBookSearchCriteria(anyString(),anyString(),anyString())).thenReturn(new ArrayList<>());
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("isbn", anyString());
        paramsMap.add("subject", anyString());
        paramsMap.add("bookName", anyString());
        mockMvc.perform(get("/book/searchBook").params(paramsMap))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void findBookSearchCriteriaExceptionTest() throws Exception {
        when(bookService.findBookSearchCriteria(anyString(),anyString(),anyString())).thenReturn(null);
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("isbn", anyString());
        paramsMap.add("subject", anyString());
        paramsMap.add("bookName", anyString());
        mockMvc.perform(get("/book/searchBook").params(paramsMap))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void updateBookTest()throws Exception {
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        given(bookService.updateBook(any(Book.class)))
                .willReturn(book);

        ResultActions response = mockMvc.perform(put("/book/updateBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookName").value(book.getBookName()))
                .andDo(print());
    }

    @Test
    public void updateBookEmptyTest()throws Exception {
        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        given(bookService.updateBook(any(Book.class)))
                .willReturn(null);

        ResultActions response = mockMvc.perform(put("/book/updateBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void deleteBookTest() throws Exception {
        doNothing().when(bookService).deleteByBookId(10);
        ResultActions response = mockMvc.perform(delete("/book/delete/{id}",10));

        response.andDo(print()).
                andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteBookExceptionTest() throws Exception {
        doThrow(new NullPointerException()).when(bookService).deleteByBookId(10);
        ResultActions response = mockMvc.perform(delete("/book/delete/{id}",10));

        response.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }
    private List<Book> getBookList() {

        List<Book> bookList = new ArrayList<>();

        Book book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        bookList.add(book);

        book = new Book();
        book.setId(12);
        book.setBookName("Java");
        book.setIsbn("1234");
        bookList.add(book);

        return bookList;
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
