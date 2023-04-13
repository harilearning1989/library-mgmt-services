package com.lib.mgmt.constants;

public class LibraryConstants {
    public static final String NO_STUDENT_FOUND_WITH_THIS_ID = "No Student Found with this StudentId:: ";
    public static final String NO_BOOK_FOUND_WITH_THIS_ID = "No Book Found with this BookId:: ";
    public static final String BOOK_ALREADY_ISSUED = "No of Same Books issued for this Student %s is %s";

    public static final String ISSUED_BOOKS_BASED_ID  = "SELECT new com.lib.mgmt.dtos.IssuedBookStudentDto" +
            "(ib.studentId,s.studentName,ib.isbn,b.bookName,ib.issuedDate) " +
            "from IssueBook ib,Student s,Book b where ib.studentId = s.studentId and ib.isbn = b.isbn and";
    public static final String ISSUED_BOOKS_BASED_STUDENT_ID = " ib.studentId =:studentId";
    public static final String ISSUED_BOOKS_BASED_BOOK_ID = " ib.isbn =:isbn";
}
