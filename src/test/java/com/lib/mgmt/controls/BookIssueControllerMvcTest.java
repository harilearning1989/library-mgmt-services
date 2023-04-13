package com.lib.mgmt.controls;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.mgmt.dtos.IssueBookDto;
import com.lib.mgmt.dtos.IssuedBookStudentDto;
import com.lib.mgmt.models.library.IssueBook;
import com.lib.mgmt.services.library.BookIssueServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookIssueController.class)
public class BookIssueControllerMvcTest {

    @MockBean
    private BookIssueServiceImpl bookIssueService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findAllBooksTest()throws Exception {
        List<IssueBook> issueBookList = getIssueBookList();
        when(bookIssueService.findAllIssuedBooks()).thenReturn(issueBookList);
        mockMvc.perform(get("/issue/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(issueBookList.size()))
                .andDo(print());
    }

    @Test
    public void findAllBooksEmptyTest()throws Exception {
        when(bookIssueService.findAllIssuedBooks()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/issue/all"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void findAllBooksExceptionTest()throws Exception {
        when(bookIssueService.findAllIssuedBooks()).thenReturn(null);
        mockMvc.perform(get("/issue/all"))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void issueNewBookTest()throws Exception {
        IssueBookDto request = new IssueBookDto();
        request.setIsbn("1234");
        request.setStudentId(76127);

        IssueBook response = new IssueBook();
        response.setIsbn("1234");
        response.setId(12);
        response.setStudentId(76127);
        given(bookIssueService.issueNewBook(any(IssueBookDto.class)))
                .willReturn(response);
        ResultActions resultActions = mockMvc.perform(post("/issue/issueBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        resultActions.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").value(response.getIsbn()))
                .andDo(print());
    }

    @Test
    public void issueNewBookExceptionTest()throws Exception {
        IssueBookDto request = new IssueBookDto();
        request.setIsbn("1234");
        request.setStudentId(76127);

        given(bookIssueService.issueNewBook(any(IssueBookDto.class)))
                .willReturn(null);
        ResultActions resultActions = mockMvc.perform(post("/issue/issueBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        resultActions.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void findIssuedBooksForStudentTest() throws Exception {
        List<IssuedBookStudentDto> issuedBookStudentDtosList = getissuedBookStudentDtos();
        given( bookIssueService.findIssuedBooksForStudent(76127))
                .willReturn(issuedBookStudentDtosList);
        ResultActions resultActions = mockMvc.perform(get("/issue/issuedBooksForStudent/{studentId}",76127));

        resultActions.andDo(print()).
                andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findIssuedBooksForStudentEmptyTest() throws Exception {
        given( bookIssueService.findIssuedBooksForStudent(76127))
                .willReturn(new ArrayList<>());
        ResultActions resultActions = mockMvc.perform(get("/issue/issuedBooksForStudent/{studentId}",76127));

        resultActions.andDo(print()).
                andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void findIssuedBooksForStudentExceptionTest() throws Exception {
        given( bookIssueService.findIssuedBooksForStudent(76127))
                .willReturn(null);
        ResultActions resultActions = mockMvc.perform(get("/issue/issuedBooksForStudent/{studentId}",76127));

        resultActions.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void sameBookIssuedForStudentsTest() throws Exception {
        List<IssuedBookStudentDto> issuedBookStudentDtosList = getissuedBookStudentDtos();
        given( bookIssueService.sameBookIssuedForStudents(76127))
                .willReturn(issuedBookStudentDtosList);
        ResultActions resultActions = mockMvc.perform(get("/issue/sameBookIssuedForStudents/{bookId}",76127));

        resultActions.andDo(print()).
                andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void sameBookIssuedForStudentsEmptyTest() throws Exception {
        given( bookIssueService.sameBookIssuedForStudents(76127))
                .willReturn(new ArrayList<>());
        ResultActions resultActions = mockMvc.perform(get("/issue/sameBookIssuedForStudents/{bookId}",76127));

        resultActions.andDo(print()).
                andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void sameBookIssuedForStudentsExceptionTest() throws Exception {
        given( bookIssueService.sameBookIssuedForStudents(76127))
                .willReturn(null);
        ResultActions resultActions = mockMvc.perform(get("/issue/sameBookIssuedForStudents/{bookId}",76127));

        resultActions.andDo(print()).
                andExpect(status().is5xxServerError())
                .andDo(print());
    }

    private List<IssuedBookStudentDto> getissuedBookStudentDtos() {
        List<IssuedBookStudentDto> issuedBookStudentDtos = new ArrayList<>();

        IssuedBookStudentDto dto = new IssuedBookStudentDto();
        dto.setBookName("Java");

        issuedBookStudentDtos.add(dto);
        return issuedBookStudentDtos;
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

    private List<IssueBook> getIssueBookList() {
        List<IssueBook> issueBookList = new ArrayList<>();
        IssueBook book = new IssueBook();
        book.setIsbn("1234");
        book.setId(12);
        book.setStudentId(76127);

        issueBookList.add(book);

        book = new IssueBook();
        book.setIsbn("1234");
        book.setId(12);
        book.setStudentId(76127);

        issueBookList.add(book);

        return issueBookList;
    }
}
