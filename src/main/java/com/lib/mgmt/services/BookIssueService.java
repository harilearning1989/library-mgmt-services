package com.lib.mgmt.services;

import com.lib.mgmt.dtos.IssueBookDto;
import com.lib.mgmt.dtos.IssuedBookStudentDto;
import com.lib.mgmt.models.IssueBook;

import java.util.List;

public interface BookIssueService {
    List<IssueBook> allIssuedBooks();

    IssueBook issueNewBook(IssueBookDto issueBook);

    List<IssuedBookStudentDto> findIssuedBooksForStudent(int studentId);

    List<IssuedBookStudentDto> sameBookIssuedForStudents(int bookId);
}
