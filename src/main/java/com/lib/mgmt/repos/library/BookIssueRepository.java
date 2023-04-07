package com.lib.mgmt.repos.library;

import com.lib.mgmt.constants.LibraryConstants;
import com.lib.mgmt.dtos.IssuedBookStudentDto;
import com.lib.mgmt.models.library.IssueBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookIssueRepository extends JpaRepository<IssueBook, Integer> {
    //List<IssueBook> findByBookIdAndStudentId(int bookId, int studentId);
    long countByBookIdAndStudentId(int bookId, int studentId);

    //int findIssuedBookCountByBookIdAndStudentId(int bookId, int studentId);

    //IssueBook findByBookIdAndStudentId(int bookId, int studentId);

    @Query(value = LibraryConstants.ISSUED_BOOKS_BASED_ID + LibraryConstants.ISSUED_BOOKS_BASED_STUDENT_ID)
    List<IssuedBookStudentDto> findIssuedBooksForStudent(int studentId);

    @Query(value = LibraryConstants.ISSUED_BOOKS_BASED_ID + LibraryConstants.ISSUED_BOOKS_BASED_BOOK_ID)
    List<IssuedBookStudentDto> sameBookIssuedForStudents(int bookId);
}
