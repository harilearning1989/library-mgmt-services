package com.lib.mgmt.services.library;

import com.lib.mgmt.dtos.IssueBookDto;
import com.lib.mgmt.dtos.IssuedBookStudentDto;
import com.lib.mgmt.models.library.IssueBook;

import java.util.List;

public interface BookIssueService {
    List<IssueBookDto> findAllIssuedBooks();

    List<IssuedBookStudentDto> findIssuedBooksForStudent(int studentId);

    IssueBook issueNewBook(IssueBookDto issueBookDto);

}
