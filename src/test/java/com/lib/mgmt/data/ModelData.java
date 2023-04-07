package com.lib.mgmt.data;

import com.lib.mgmt.dtos.IssuedBookStudentDto;
import com.lib.mgmt.models.Book;
import com.lib.mgmt.models.IssueBook;
import com.lib.mgmt.models.ReturnBook;

import java.util.ArrayList;
import java.util.List;

public class ModelData {

    public static List<Book> getBookList() {
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
    public static List<IssueBook> getIssueBookList() {
        List<IssueBook> issueBookList = new ArrayList<>();
        IssueBook book = new IssueBook();
        book.setBookId(1234);
        book.setId(12);
        book.setStudentId(76127);

        issueBookList.add(book);

        book = new IssueBook();
        book.setBookId(1235);
        book.setId(13);
        book.setStudentId(76128);

        issueBookList.add(book);

        book = new IssueBook();
        book.setBookId(1236);
        book.setId(14);
        book.setStudentId(76129);

        issueBookList.add(book);

        return issueBookList;
    }

    public static List<IssuedBookStudentDto> getIssuedBookStudentDtos() {
        List<IssuedBookStudentDto> issuedBookStudentDtos = new ArrayList<>();

        IssuedBookStudentDto dto = new IssuedBookStudentDto();
        dto.setStudentName("Hari");
        dto.setBookName("Java");

        issuedBookStudentDtos.add(dto);

        return issuedBookStudentDtos;
    }

    public static List<ReturnBook> getReturnBookData() {
        List<ReturnBook> returnBookList = new ArrayList<>();
        ReturnBook returnBook = new ReturnBook();
        returnBook.setBookId(123);
        returnBook.setStudentId(76127);

        returnBookList.add(returnBook);

        return returnBookList;
    }
}
