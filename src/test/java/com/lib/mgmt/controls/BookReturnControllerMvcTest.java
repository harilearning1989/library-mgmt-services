package com.lib.mgmt.controls;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lib.mgmt.data.ModelData;
import com.lib.mgmt.models.library.ReturnBook;
import com.lib.mgmt.services.BookReturnServiceImpl;
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

@WebMvcTest(BookReturnController.class)
public class BookReturnControllerMvcTest {

    @MockBean
    private BookReturnServiceImpl bookReturnService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void allReturnedBooksTest()throws Exception {
        List<ReturnBook> returnBookData = ModelData.getReturnBookData();
        when(bookReturnService.allReturnedBooks()).thenReturn(returnBookData);
        mockMvc.perform(get("/return/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(returnBookData.size()))
                .andDo(print());
    }

    @Test
    public void allReturnedBooksEmptyTest()throws Exception {
        when(bookReturnService.allReturnedBooks()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/return/all"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void allReturnedBooksExceptionTest()throws Exception {
        when(bookReturnService.allReturnedBooks()).thenReturn(null);
        mockMvc.perform(get("/return/all"))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    public void returnOldBookTest() throws Exception {
        ReturnBook returnBook = new ReturnBook();
        returnBook.setBookId(123);
        returnBook.setStudentId(76127);
        given(bookReturnService.returnOldBook(any(ReturnBook.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/return/returnBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(returnBook)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentId").value(returnBook.getStudentId()));
    }

    @Test
    public void returnOldBookExceptionTest() throws Exception {
        ReturnBook returnBook = new ReturnBook();
        returnBook.setBookId(123);
        returnBook.setStudentId(76127);

        given(bookReturnService.returnOldBook(any(ReturnBook.class)))
                .willReturn(null);

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/return/returnBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(returnBook)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().is5xxServerError());
    }

}
