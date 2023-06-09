package com.lib.mgmt.services.library;

import com.lib.mgmt.dtos.IssueBookDto;
import com.lib.mgmt.models.library.IssueBook;
import com.lib.mgmt.models.library.ReturnBook;

import java.util.List;

public interface BookReturnService {
    List<ReturnBook> allReturnedBooks();

    ReturnBook returnOldBook(ReturnBook returnBook);
    IssueBook returnIssuedBook(IssueBookDto issueBookDto);
}
